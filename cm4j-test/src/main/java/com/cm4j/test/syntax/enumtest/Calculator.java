package com.cm4j.test.syntax.enumtest;

/**
 * 枚举策略类
 * @author  yanghao
 * @since  2010-5-10
 */
public enum Calculator {

	/**
	 * @uml.property  name="aDD"
	 * @uml.associationEnd  
	 */
	ADD("+") {
		@Override
		public int exec(int a, int b) {
			return a + b;
		}
	},

	/**
	 * @uml.property  name="sUB"
	 * @uml.associationEnd  
	 */
	SUB("-") {
		@Override
		public int exec(int a, int b) {
			return a - b;
		}
	};

	/**
	 * @uml.property  name="value"
	 */
	String value = "";

	/**
	 *  构造函数 - 必须有
	 * @param _value
	 */
	private Calculator(String _value) {
		this.value = _value;
	}

	/**
	 * 获取枚举成员的值
	 * @return
	 * @uml.property  name="value"
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * 申明一个抽象方法，则每个成员变量必须实现该方法
	 * @param a
	 * @param b
	 * @return
	 */
	public abstract int exec(int a, int b);
}
