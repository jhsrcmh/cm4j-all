package com.cm4j.test.designpattern.strategy.ordinary;

import com.cm4j.test.designpattern.strategy.ordinary.countrytax.CalcTax;

public class SalesOrder {

	public void calcTax(CalcTax taxToUse) {
		long itemSold = 0;
		double price = 0;
		double tax = taxToUse.taxAmount(itemSold, price);
		System.out.println(tax);
	}

}
