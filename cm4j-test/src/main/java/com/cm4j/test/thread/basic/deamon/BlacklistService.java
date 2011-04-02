package com.cm4j.test.thread.basic.deamon;

import java.io.File;

/**
 * Daemon线程示例： 黑名单服务 - 配置一修改，服务器无需修改即可重新加载
 * 
 * @author 2011-3-21
 */
public class BlacklistService {
	private File configFile = new File("c:/blacklist.txt");

	public void init() throws Exception {
		loadConfig();
		// 启动守护线程
		ConfigWatchDog dog = new ConfigWatchDog();
		dog.setName("daemon_demo_config_watchdog");// a
		dog.addFile(configFile);// b
		dog.start();// c
	}

	public void loadConfig() {
		try {
			Thread.sleep(1 * 1000);// d

			System.out.println("加载黑名单");
		} catch (InterruptedException ex) {
			System.out.println("加载配置文件失败！");
		}
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	private class ConfigWatchDog extends FileWatchdog {
		@Override
		protected void doOnChange(File file) {
			System.out.println("文件" + file.getName() + "发生改变，重新加载");
			loadConfig();
		}
	}

	/**
	 * main方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		BlacklistService service = new BlacklistService();
		service.init();

		Thread.sleep(60 * 60 * 1000);// e
	}
}