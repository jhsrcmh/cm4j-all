package com.cm4j.test.designpattern.strategy.ordinary.main;

import com.cm4j.test.designpattern.strategy.ordinary.SalesOrder;
import com.cm4j.test.designpattern.strategy.ordinary.countrytax.CalcTax;
import com.cm4j.test.designpattern.strategy.ordinary.countrytax.USTax;

public class TaskController {
	public void process() {
		CalcTax muTax = getTaxRuleForCountry();
		SalesOrder order = new SalesOrder();
		order.calcTax(muTax);
	}
	
	private CalcTax getTaxRuleForCountry(){
		return new USTax();
	}
	
	public static void main(String[] args) {
		TaskController controller = new TaskController();
		controller.process();
	}
}
