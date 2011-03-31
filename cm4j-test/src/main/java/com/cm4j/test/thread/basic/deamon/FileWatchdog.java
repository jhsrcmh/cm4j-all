package com.cm4j.test.thread.basic.deamon;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 抽象类，本身是线程的子类；在构造函数中设置为守护线程；
 * 此类用hashmap维护着一个文件和最新修改时间值对，checkAndConfigure()方法用来检测哪些文件的修改时间更新了
 * ，如果发现文件更新了则调用doOnChange方法来完成监测逻辑；doOnChange方法是我们需要实现的
 * 
 * @author yanghao
 * 
 */
public abstract class FileWatchdog extends Thread {

	static final public long DEFAULT_DELAY = 3 * 1000;

	protected HashMap<String, Entity> fileList;

	protected long delay = DEFAULT_DELAY;

	boolean warnedAlready = false;

	boolean interrupted = false;

	public static class Entity {
		File file;
		long lastModify;

		Entity(File file, long lastModify) {
			this.file = file;
			this.lastModify = lastModify;
		}
	}

	protected FileWatchdog() {
		fileList = new HashMap<String, Entity>();
		// 设置为守护线程
		setDaemon(true);
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public void addFile(File file) {
		fileList.put(file.getAbsolutePath(), new Entity(file, file.lastModified()));
	}

	public boolean contains(File file) {
		if (fileList.get(file.getAbsolutePath()) != null)
			return true;
		else
			return false;
	}

	/**
	 * 在文件修改时进行的操作
	 * 
	 * @param file
	 */
	abstract protected void doOnChange(File file);

	/**
	 * 检查配置文件
	 */
	protected void checkAndConfigure() {
		@SuppressWarnings("unchecked")
		HashMap<String, Entity> map = (HashMap<String, Entity>) fileList.clone();
		Iterator<Entity> it = map.values().iterator();

		System.out.println("检查配置是否改动....");
		while (it.hasNext()) {
			Entity entity = it.next();
			boolean fileExists;
			try {
				fileExists = entity.file.exists();
			} catch (SecurityException e) {
				System.err.println("Was not allowed to read check file existance, file:["
						+ entity.file.getAbsolutePath() + "].");
				interrupted = true;
				return;
			}

			if (fileExists) {
				long l = entity.file.lastModified(); // 最后修改时间，可能会抛SecurityException
				if (l > entity.lastModify) { // 文件被修改
					entity.lastModify = l; // 将map中的entity的最后修改时间改为当前文件修改时间
					doOnChange(entity.file); // 重新加载配置
				}
			} else {
				System.err.println("[" + entity.file.getAbsolutePath() + "] does not exist.");
			}
		}
	}

	@Override
	public void run() {
		while (!interrupted) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// no interruption expected
			}
			checkAndConfigure();
		}
	}
}
