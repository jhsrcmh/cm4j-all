package com.cm4j.test.syntax.consistenthash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性哈希算法实现，consistent hashing
 * http://weblogs.java.net/blog/2007/11/27/consistent-hashing
 * 
 * @author yang.hao
 * @since 2011-8-15 上午09:49:16
 * 
 * @param <T>
 */
public class ConsistentHash<T> {
	private final HashFunction hashFunction;
	private final int numberOfReplicas;
	private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

	public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;
		for (T node : nodes) {
			add(node);
		}
	}

	public void add(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashFunction.hash(node.toString() + i), node);
		}
	}

	public void remove(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashFunction.hash(node.toString() + i));
		}
	}

	public T get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}
		int hash = hashFunction.hash(key);
		if (!circle.containsKey(hash)) {
			SortedMap<Integer, T> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}
}

/**
 * 自定义hash算法，MD5 hashes are recommended here.
 * 
 * @author yang.hao
 * @since 2011-8-15 上午09:48:44
 * 
 */
class HashFunction {
	public int hash(Object value) {
		return value.hashCode();
	}
}
