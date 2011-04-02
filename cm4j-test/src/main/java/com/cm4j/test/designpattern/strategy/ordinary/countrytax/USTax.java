package com.cm4j.test.designpattern.strategy.ordinary.countrytax;

public class USTax extends CalcTax {

	@Override
	public double taxAmount(long itemSold, double price) {
		// TODO Auto-generated method stub
		return 100.230;
	}

}
