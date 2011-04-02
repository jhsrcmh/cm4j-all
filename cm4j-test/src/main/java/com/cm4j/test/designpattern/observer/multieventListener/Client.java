package com.cm4j.test.designpattern.observer.multieventListener;

import java.util.EventObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 一个完整的事件过程主要包含如下几个要素：
 * 事件源（Source）：产生事件的对象
 * 事件监听器（EventListener）：负责监听事件并定义相应的事件处理过程
 *      onApplicationEvent(Event  event)
 * 事件（EventObject）：标识、存放事件源和源对象无法传递的参数
 *      Event构造函数需传入Source对象，这样EventListener调用时可获取Source对象
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		try {
			// 实例化事件源
			ActualEventSource demoSource = new ActualEventSource();

			// 事件源注册监听器
			demoSource.registerListener(new DemoListener() {
				public void onEvent(EventObject event) {
					ActualEventSource source = (ActualEventSource) event.getSource();
					logger.debug("sourceName:" + source.getName());
				}
			});

			// 事件源执行业务逻辑
			demoSource.exec();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
