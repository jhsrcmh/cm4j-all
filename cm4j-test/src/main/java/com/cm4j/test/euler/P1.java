package com.cm4j.test.euler;


/**
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we
 * get 3, 5, 6 and 9. The sum of these multiples is 23.
 * 
 * Find the sum of all the multiples of 3 or 5 below 1000.
 * 
 * 求1000以下是3或5的倍数的正整数之和
 * 
 * @author yang.hao
 * @since 2011-6-21 下午03:59:40
 * 
 */
public class P1 {

	public static void main(String[] args) {
		int set = 1000;

		int sum = 0;
		for (int i = 1; i < set; i++) {
			if (i % 3 == 0 || i % 5 == 0) {
				sum += i;
				System.out.println("num:" + i);
			}
		}
		System.out.println(sum);
	}
}
