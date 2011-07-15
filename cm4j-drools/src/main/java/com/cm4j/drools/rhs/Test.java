package com.cm4j.drools.rhs;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;

import com.cm4j.drools.lhs.Customer;

public class Test {
	public static void main(String[] args) {
		KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kb.add(new ClassPathResource("insert.drl", Test.class), ResourceType.DRL);
		Collection<KnowledgePackage> collection = kb.getKnowledgePackages();

		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(collection);
		StatefulKnowledgeSession statefulSession = knowledgeBase.newStatefulKnowledgeSession();

		Customer customer = new Customer();
		customer.setName("张三");
		customer.setAge(20);
		statefulSession.insert(customer);

		statefulSession.fireAllRules();
		statefulSession.dispose();

		QueryResults qr = statefulSession.getQueryResults("query fact count");
		System.out.println("customer 对象数目：" + qr.size());

		System.out.println("end.....");
	}
}
