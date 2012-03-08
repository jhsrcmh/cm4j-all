package com.woniu.network.events;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 事件分发器
 * 
 * @author yang.hao
 * @since 2011-11-16 下午4:13:32
 */
@SuppressWarnings("rawtypes")
public class EventMulticaster {

	private Set<ApplicationListener> listeners = new CopyOnWriteArraySet<ApplicationListener>();

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	public void addListener(ApplicationListener listenter) {
		listeners.add(listenter);
	}

	/**
	 * 事件分发
	 * 
	 * @param event
	 */
	public void multicastEvent(final ApplicationEvent event) {
		Set<ApplicationListener<ApplicationEvent>> list = getApplicationListeners(event);
		for (final ApplicationListener<ApplicationEvent> applicationListener : list) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					applicationListener.onEvent(event);
				}
			});
		}
	}

	private final Map<ListenerCacheKey, Set<ApplicationListener<ApplicationEvent>>> retrieverCache = new ConcurrentHashMap<EventMulticaster.ListenerCacheKey, Set<ApplicationListener<ApplicationEvent>>>();

	private Set<ApplicationListener<ApplicationEvent>> getApplicationListeners(ApplicationEvent event) {
		Class<? extends ApplicationEvent> eventType = event.getClass();
		Class sourceType = event.getSource().getClass();
		ListenerCacheKey cacheKey = new ListenerCacheKey(eventType, sourceType);

		Set<ApplicationListener<ApplicationEvent>> result = this.retrieverCache.get(cacheKey);
		if (result != null) {
			return result;
		} else {
			result = new LinkedHashSet<ApplicationListener<ApplicationEvent>>();
			Class<? extends ApplicationEvent> cls = event.getClass();
			for (ApplicationListener<ApplicationEvent> listener : listeners) {
				Type _interface = listener.getClass().getGenericInterfaces()[0];
				if (_interface instanceof ParameterizedType) {
					Class<?> _event_type = (Class) ((ParameterizedType) (_interface)).getActualTypeArguments()[0];
					if (_event_type.isAssignableFrom(cls)) {
						result.add(listener);
					}
				} else {
					// 无范型
					result.add(listener);
				}
			}
			if (!result.isEmpty()) {
				this.retrieverCache.put(cacheKey, result);
			}
		}
		return result;
	}

	private static class ListenerCacheKey {
		private final Class eventType;

		private final Class sourceType;

		public ListenerCacheKey(Class eventType, Class sourceType) {
			this.eventType = eventType;
			this.sourceType = sourceType;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			ListenerCacheKey key = (ListenerCacheKey) obj;
			return (this.eventType.equals(key.eventType) && this.sourceType.equals(key.sourceType));
		}
		
		@Override
		public int hashCode() {
			return this.eventType.hashCode() * 29 + this.sourceType.hashCode();
		}
	}
}
