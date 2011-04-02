package com.cm4j.test.designpattern.observer.multieventListener;

import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationListener;

/**
 * 事件分发器
 * 
 * @author yang.hao
 * @since 2011-4-1 下午02:57:15
 * 
 */
public class EventMulticaster {

	private final Set<DemoListener> repository = new LinkedHashSet<DemoListener>();

	private final Map<KeyValue, EventListener> listenerMap = new HashMap<KeyValue, EventListener>();

	public void registerListener(DemoListener dl) {
		repository.add(dl);
	}

	public Collection<ApplicationListener> getApplicationListeners(EventObject e) {
		Class<? extends EventObject> eventClass = e.getClass();
		Class<? extends Object> sourceClass = e.getSource().getClass();

		KeyValue keyValue = new KeyValue(eventClass, sourceClass);
		EventListener listener = listenerMap.get(keyValue);
		if (listener == null) {
			Iterator<DemoListener> enu = repository.iterator();
			while (enu.hasNext()) {
				DemoListener demoListener = enu.next();
				if (e instanceof DemoListener){
					
				}
			}
		}
		return null;
	}

	/**
	 * 调用已注册的监听接口
	 */
	public void publishEvent(EventObject e) {

	}

	private class KeyValue {
		private Class<? extends EventObject> eventClass;
		private Class<? extends Object> sourceClass;

		public KeyValue(Class<? extends EventObject> eventClass, Class<? extends Object> sourceClass) {
			this.eventClass = eventClass;
			this.sourceClass = sourceClass;
		}

	}

}
