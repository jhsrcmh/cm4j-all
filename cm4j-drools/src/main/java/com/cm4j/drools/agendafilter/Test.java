package com.cm4j.drools.agendafilter;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.StatefulKnowledgeSession;

import com.cm4j.drools.lhs.Customer;

public class Test {
	
	public static void print (Object toPrint){
		System.out.println(toPrint);
	}
	
	public static void main(String[] args) throws InterruptedException {
		KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();

		kb.add(new ClassPathResource("filter.drl", Test.class), ResourceType.DRL);
		Collection<KnowledgePackage> collection = kb.getKnowledgePackages();

		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(collection);
		StatefulKnowledgeSession statefulSession = knowledgeBase.newStatefulKnowledgeSession();

		Customer customer = new Customer();
		customer.setAge(1);
		statefulSession.insert(customer);
		
		// 指定过滤条件
		statefulSession.fireAllRules(new AgendaFilterImpl("rule"));
		statefulSession.dispose();

	}
}
