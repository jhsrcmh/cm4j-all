package com.cm4j.test.syntax;

/**
 * <pre>
 * 结论：
 * 对于基本类型：== 判断2个变量值是否相等，equals不适用
 * 对于对象变量：== 对比2个变量的内存地址，equals对比2个对象的值
 * 
 * 总之： 
 * “==”比较的是值【变量(栈)内存中存放的对象的(堆)内存地址】 
 * equals用于比较两个对象的值是否相同【不是比地址】 
 * 
 * 【特别注意】Object类中的equals方法和“==”是一样的，没有区别，而String类，Integer类等等一些类，
 * 是重写了equals方法，才使得equals和“==不同”，所以，当自己创建类时，自动继承了Object的equals方法，
 * 要想实现不同的等于比较，必须重写equals方法。
 * 
 * "=="比"equal"运行速度快,因为"=="只是比较引用.
 * </pre>
 * 
 * @author yang.hao
 * @since 2011-5-17 上午09:32:08
 * 
 */
public class EqualsAndDoubleEquals {
	public static void main(String[] args) {
		int t1 = 57;
		int t2 = 67;
		int t3 = 124;
		int t4 = 124;
		// “==”对于基本数据类型，判断两个变量的值是否相等。
		Boolean result1 = (t1 == t2); // false
		Boolean result2 = ((t1 + t2) == t3); // true
		Boolean result3 = (t3 == t4); // true

		System.out
				.println("\n\n-----【t1==t2】" + result1 + "\n-----【(t1+t2)=t3】" + result2 + "\n-----【t3=t4】" + result3);

		/********************************************************************/

		// “equal”不能用于基本数据类型。只能用于类变量。对于基本数据类型要用其包装类。
		Integer i1 = new Integer(t1);
		Integer i2 = new Integer(t2);
		Integer i3 = new Integer(t3);
		Integer i4 = new Integer(t4);

		Boolean ri1 = i1.equals(i2); // false
		Boolean ri2 = i3.equals(i1 + i2); // true
		Boolean ri3 = i3.equals(i4); // true

		System.out.println("\n\n-----【i1.equals(i2)】" + ri1 + "\n-----【i3.equals(i1+i2)】" + ri2
				+ "\n-----【i3.equals(i4)】" + ri3);

		/********************************************************************/

		// 程序在运行的时候会创建一个字符串缓冲池，当使用 st4 = "wasiker is super man" 这样的表达是创建字符串的时候，
		// 程序首先会在这个String缓冲池中寻找相同值的对象，在第一个程序中，s1先被放到了池中，所以在s2被创建的时候，
		// 程序找到了具有相同值的 s1，将 s2 引用 s1 所引用的对象"Monday"
		String st1 = "wasiker ";
		String st2 = "is super man";
		String st3 = "wasiker is super man";
		String st4 = "wasiker is super man";
		String st5 = new String("wasiker is super man");

		Boolean b1 = (st1 == st2); // false
		Boolean b2 = (st1 + st2) == st3; // false
		Boolean b3 = (st3 == st4); // true
		Boolean b4 = (st4 == st5); // false

		System.out.println("\n\n-----【st1==st2】" + b1 + "\n-----【(st1+st2)==st3】" + b2 + "\n-----【st3==st4】" + b3);
		System.out.println("【st4==st5】" + b4);

		/********************************************************************/

		// 因为对象变量的存储的是对象在内存中的路径，即内存地址。所以用“==”比较时，即使
		// 对象的值相等，但是他们的内存地址不同，所以==的结果为false。故“==”用于比较两
		// 个变量的值是否相等，而不是变量引用的对象是否相等

		Boolean r1 = st1.equals(st2); // false
		Boolean r2 = (st1 + st2).equals(st3); // true
		Boolean r3 = st3.equals(st4); // true

		System.out.println("\n\n-----【st1.equals(st2)】" + r1 + "\n-----【(st1+st2).equals(st3)】" + r2
				+ "\n-----【st3.equals(st4)】" + r3);

		// equals用于比较两个对象是否相同。

		Integer a1 = 12, a2 = 12, a3 = 24;
		System.out.println(a1 == a2);
		System.out.println((a1 + a2) == a3);
	}
}
