package com.cm4j.test.syntax.nio.netty.core;

import java.util.Date;

public class T4_UnixTime {
	private int value;

	public T4_UnixTime(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return new Date(this.value * 1000).toString();
	}
}
