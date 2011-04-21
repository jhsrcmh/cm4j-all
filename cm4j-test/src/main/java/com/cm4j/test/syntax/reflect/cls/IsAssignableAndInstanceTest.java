package com.cm4j.test.syntax.reflect.cls;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

public class IsAssignableAndInstanceTest {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		// 测试isAssignableFrom
		System.out.println(String.class.isAssignableFrom(Object.class));// false
		System.out.println(Comparable.class.isAssignableFrom(String.class));// true
		System.out.println(Set.class.isAssignableFrom(Collection.class));// false
		System.out.println(Collection.class.isAssignableFrom(Set.class));// true
		System.out.println(Set.class.isAssignableFrom(HashSet.class));// true
		// 测试instanceof
		System.out.println("===============================================");
		Comparable com = new String();
		System.out.println(com instanceof String);// true
		Hashtable table = new Hashtable();
		System.out.println(table instanceof Properties);// false
	}
}
