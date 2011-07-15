package com.cm4j.drools.properties;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.StatefulKnowledgeSession;

public class Test {
	
	public static void main(String[] args) throws InterruptedException {
		KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();

		// 自定义drools日期格式 在pros.drl中有日期限制
		System.setProperty("drools.dateformat", "yyyy-MM-dd hh:mm:ss");

		kb.add(new ClassPathResource("pros.drl", Test.class), ResourceType.DRL);
		Collection<KnowledgePackage> collection = kb.getKnowledgePackages();

		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(collection);
		StatefulKnowledgeSession statefulSession = knowledgeBase.newStatefulKnowledgeSession();

		// 设置启用的agenda组
		statefulSession.getAgenda().getAgendaGroup("001").setFocus();
		
		statefulSession.fireAllRules();
		statefulSession.dispose();

		Thread.sleep(2000);
		System.out.println("main thread id:" + Thread.currentThread().getId());
	}
}
