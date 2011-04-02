package com.cm4j.test.designpattern.strategy.ordinary.countrytax;

public class CanadaTax extends CalcTax {

	@Override
	public double taxAmount(long itemSold, double price) {
		// TODO Auto-generated method stub
		return 10.0;
	}

}
