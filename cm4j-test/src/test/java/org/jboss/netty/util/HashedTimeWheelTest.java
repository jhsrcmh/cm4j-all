package org.jboss.netty.util;

import java.util.concurrent.TimeUnit;

public class HashedTimeWheelTest {

	public static void main(String[] args) {
		Timer timer = new HashedWheelTimer();
		timer.newTimeout(new TimerTask() {
			@Override
			public void run(Timeout timeout) throws Exception {
				System.out.println("time out");
			}
		}, 1, TimeUnit.SECONDS);

		System.out.println("done ...");
	}
}
