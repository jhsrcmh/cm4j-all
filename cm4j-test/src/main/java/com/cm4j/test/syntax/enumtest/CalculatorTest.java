package com.cm4j.test.syntax.enumtest;

public class CalculatorTest {

	public static void main(String[] args) {
		System.out.println(Calculator.ADD.getValue());
		System.out.println(Calculator.ADD.exec(1, 2));
		System.out.println(Calculator.SUB.exec(1, 2));
	}
}
