package com.cm4j.test.syntax.nio.netty.core.code;

import java.util.Date;

public class UnixTime {
	private int value;

	public UnixTime(int value) {
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
