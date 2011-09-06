package com.cm4j.test.thread.concurrent.queue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 带优先级的阻塞队列
 * 
 * @author yang.hao
 * @since 2011-9-5 下午2:16:38
 */
public class PriorityBlockingQueueUsage {

	private static final int MAX_PARKING = 50;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PriorityBlockingQueueUsage instance = new PriorityBlockingQueueUsage();

		instance.busEnter(new Bus("粤A12345", 2, 5));
		instance.busEnter(new Bus("粤A88888", 1, 2));
		instance.busEnter(new Bus("粤A66666", 2, 6));
		instance.busEnter(new Bus("粤A33333", 1, 7));
		instance.busEnter(new Bus("粤A21123", 2, 1));
		instance.busEnter(new Bus("粤AGG892", 1, 4));
		instance.busEnter(new Bus("粤AJJ000", 2, 8));

		while (true) {
			instance.busQuit();
		}
	}

	private final PriorityBlockingQueue<Bus> busStation = new PriorityBlockingQueue<Bus>(MAX_PARKING);

	/**
	 * 车辆进站
	 * 
	 * @param bus
	 */
	private void busEnter(Bus bus) {
		// System.out.println("进站-->"+bus.toString());
		if (busStation.size() < MAX_PARKING)
			busStation.add(bus);
		else
			System.out.println("站内车位已满");

	}

	/**
	 * 车辆出站
	 */
	private void busQuit() {
		try {
			Bus bus = busStation.take();
			System.out.println("出站-->" + bus.toString());
		} catch (InterruptedException e) {

		}
	}

	/**
	 * 车实例
	 * 
	 * @author jiangw
	 * 
	 *         2010-3-26
	 */
	static class Bus implements Comparable<Bus> {

		private String busNo;
		private Integer busType;
		private Integer level;

		private Bus() {
		}

		private Bus(String busNo, Integer busType, Integer level) {
			super();
			this.busNo = busNo;
			this.busType = busType;
			this.level = level;
		}

		public String getBusNo() {
			return busNo;
		}

		public void setBusNo(String busNo) {
			this.busNo = busNo;
		}

		public Integer getBusType() {
			return busType;
		}

		public void setBusType(Integer busType) {
			this.busType = busType;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

		@Override
		public int compareTo(Bus o) {
			if (o instanceof Bus) {
				return (level > o.level) ? 1 : -1;
			}
			return 0;
		}

		@Override
		public String toString() {
			return "当前车信息：种类[" + busType + "]车牌[" + busNo + "]优先级[" + level + "]";
		}
	}

}
