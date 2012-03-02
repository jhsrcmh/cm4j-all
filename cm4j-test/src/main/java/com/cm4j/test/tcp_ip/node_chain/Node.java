package com.cm4j.test.tcp_ip.node_chain;

public class Node {

	/**
	 * 上一节点
	 */
	private Node prev;
	/**
	 * 下一节点
	 */
	private Node next;

	/**
	 * 请求参数
	 */
	private Object request;
	/**
	 * 返回值
	 */
	private Object response;
	
	/**	
	 * 索引
	 */
	private int index;
	
	public Node(int index) {
		this.index = index;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public int getIndex() {
		return index;
	}

}
