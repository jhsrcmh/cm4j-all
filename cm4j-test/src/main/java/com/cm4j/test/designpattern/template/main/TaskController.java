package com.cm4j.test.designpattern.template.main;

import com.cm4j.test.designpattern.template.BaseClass;
import com.cm4j.test.designpattern.template.SubClass2;

public class TaskController {
	public static void main(String[] args) {
		BaseClass cls = new SubClass2();
		cls.process();
	}
}
