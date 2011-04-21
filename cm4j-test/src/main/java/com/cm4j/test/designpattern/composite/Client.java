package com.cm4j.test.designpattern.composite;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 关键点：可向上或向下迭代查找父节点或子节点
 * 
 * @author yang.hao
 * @since 2011-5-17 上午11:23:32
 * 
 */
public class Client {

	public static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		Branch root = new Branch("root", "manager", 10000);

		Branch develop = new Branch("develop", "dev", 8000);
		Branch develop1 = new Branch("develop1", "dev", 4000);
		Branch develop2 = new Branch("develop2", "dev", 9000);

		Leaf a = new Leaf("a", "door", 3500);
		Leaf b = new Leaf("b", "door", 2500);
		Leaf c = new Leaf("c", "door", 4000);
		Leaf d = new Leaf("d", "door", 2500);
		Leaf e = new Leaf("e", "door", 4500);

		root.add(develop);
		root.add(develop1);
		root.add(develop2);

		develop.add(a);
		develop1.add(b);
		develop1.add(c);
		develop2.add(d);
		develop2.add(e);

		getTreeInfo(root);

		findFathers(e);
	}

	/**
	 * 循环树形结构所有节点 - 递归
	 * 
	 * @param root
	 */
	public static void getTreeInfo(Branch branch) {
		System.out.println(branch.getInfo());
		ArrayList<Corp> sublist = branch.getSubordinateInfo();
		for (Corp corp : sublist) {
			if (!(corp instanceof Leaf))
				getTreeInfo((Branch) corp);
			else
				System.out.println(corp.getInfo());
		}
	}

	/**
	 * 向上查找所有的父类
	 * 
	 * @param corp
	 */
	public static void findFathers(Corp corp) {
		logger.debug("this:{}", corp.getInfo());
		Corp father = corp.getParent();
		if (father != null) {
			logger.debug("father:{}", father.getInfo());
			findFathers(father);
		}
	}
}
