package com.cm4j.drools.query;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

import com.cm4j.drools.lhs.Customer;

public class Test {
	public static void main(String[] args) {
		KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kb.add(new ClassPathResource("query.drl", Test.class), ResourceType.DRL);
		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(kb.getKnowledgePackages());
		StatefulKnowledgeSession statefulSession = knowledgeBase.newStatefulKnowledgeSession();
		// 向当前WorkingMemory当中插入Customer对象
		statefulSession.insert(generateCustomer("张三", 20));
		statefulSession.insert(generateCustomer("李四", 33));
		statefulSession.insert(generateCustomer("王二", 43));
		// 调用查询
		QueryResults queryResults = statefulSession.getQueryResults("query customer");
		for (QueryResultsRow qr : queryResults) {
			Customer cus = (Customer) qr.get("customer");
			// 打印查询结果
			System.out.println("customer name :" + cus.getName());
		}
		statefulSession.dispose();
	}

	/**
	 * 产生包括指定数量Order的Customer
	 * */
	public static Customer generateCustomer(String name, int age) {
		Customer cus = new Customer();
		cus.setName(name);
		cus.setAge(age);
		return cus;
	}
}