package com.cm4j.test.designpattern.chain.nettychain;

public class Client {

	public static void main(String[] args) {
		HandlerChain chain = new HandlerChain();
		chain.addLast("h1", new Handler() {

			@Override
			public void handle() {
				System.out.println("h1");
			}
		});

		chain.addLast("h2", new Handler() {

			@Override
			public void handle() {
				System.out.println("h2");
			}
		});

		chain.addBefore("h2", "h3", new Handler() {

			@Override
			public void handle() {
				System.out.println("h3");
			}
		});

		chain.addAfter("h2", "h5", new Handler() {

			@Override
			public void handle() {
				System.out.println("h5");
			}
		});

		chain.addFirst("h4", new Handler() {

			@Override
			public void handle() {
				System.out.println("h4");
			}
		});

		chain.handleUpstream();
	}
}
