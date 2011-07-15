package com.cm4j.drools.lhs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

	public static Logger logger = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) {
		// KnowledgeBuilder的作用就是用来在业务代码当中收集已经编写好的规则，
		// 然后对这些规则文件进行编译，最终产生一批编译好的规则包（KnowledgePackage）给其它的应用程序使用。
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("test.drl", Test.class), ResourceType.DRL);

		// 校验规则文件
		if (kbuilder.hasErrors()) {
			System.out.println("规则中存在错误，错误消息如下：");
			KnowledgeBuilderErrors kbuidlerErrors = kbuilder.getErrors();
			for (Iterator<KnowledgeBuilderError> iter = kbuidlerErrors.iterator(); iter.hasNext();) {
				System.out.println(iter.next());
			}
		}

		Collection<KnowledgePackage> kpackage = kbuilder.getKnowledgePackages();// 产生规则包的集合

		// KnowledgeBase 是 Drools 提供的用来收集应用当中知识（knowledge）定义的知识库对象，在一个
		// KnowledgeBase 当中可以包含普通的规则（rule）、规则流(rule
		// flow)、函数定义(function)、用户自定义对象（type model）等。
		KnowledgeBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		kbConf.setProperty("org.drools.sequential", "true");
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
		kbase.addKnowledgePackages(kpackage);// 将KnowledgePackage集合添加到KnowledgeBase当中

		// StatefulKnowledgeSession 对象是一种最常用的与规则引擎进行交互的方式
		// StatefulKnowledgeSession 可以接受外部插入（insert）的业务数据 -face对象-pojo
		// 果在规则当中需要有数据传出，可通过在 StatefulKnowledgeSession 当中设置 global 对象来实现
		StatefulKnowledgeSession statefulKSession = kbase.newStatefulKnowledgeSession();
		// statefulKSession.setGlobal("logger", logger);// 设置一 个global对象

		Customer cus1 = new Customer();
		cus1.setName("张三");
		Customer cus2 = new Customer();
		cus2.setName("李四");
		Customer cus3 = new Customer();
		cus3.setName("王二");
		Customer cus4 = new Customer();
		cus4.setName("李小龙");

		statefulKSession.insert(cus1);
		statefulKSession.insert(cus2);
		statefulKSession.insert(cus3);
		statefulKSession.insert(cus4);

		// 触发规则执行
		statefulKSession.fireAllRules();
		// 释放资源，必须有
		statefulKSession.dispose();

		logger.debug("" + cus2.getAge());

		// 对StatefulKnowledgeSession的封装
		StatelessKnowledgeSession statelessKSession = kbase.newStatelessKnowledgeSession();
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(new Object());
		list.add(new Object());
		statelessKSession.execute(list);
	}
}