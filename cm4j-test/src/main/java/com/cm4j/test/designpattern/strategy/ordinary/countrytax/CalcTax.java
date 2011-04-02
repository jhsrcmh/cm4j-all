package com.cm4j.test.designpattern.strategy.ordinary.countrytax;

public abstract class CalcTax {

	abstract public double taxAmount(long itemSold, double price);

}