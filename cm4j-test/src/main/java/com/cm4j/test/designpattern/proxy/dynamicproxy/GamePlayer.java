package com.cm4j.test.designpattern.proxy.dynamicproxy;

public class GamePlayer implements IGamePlayer {

	private String name;
	
	public GamePlayer(String name) {
		this.name = name;
	}
	
	@Override
	public void login(String user, String pwd) {
		System.out.println("login:user=" + user + ",pwd=" + pwd);
	}

	@Override
	public void killBoss() {
		System.out.println(this.name + "killBoss()");
	}

	@Override
	public void upgrade() {
		System.out.println(this.name + "upgrade()");
	}

}
