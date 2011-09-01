package com.cm4j.taobao.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

public class HttpClientUtils {

	/**
	 * 单例对象
	 * 
	 * @author yang.hao
	 * @since 2011-8-31 下午9:19:04
	 */
	public static class Holder {
		private static HttpClient client = new DefaultHttpClient();

		public static HttpClient getInstance() {
			return client;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String exec(HttpUriRequest request, ResponseHandler handler) throws HttpException {
		try {
			return Holder.getInstance().execute(request, handler);
		} catch (Exception e) {
			throw new HttpException(e);
		} finally {
			Holder.getInstance().getConnectionManager().shutdown();
		}
	}

	public static String get(String url, HttpParams params) throws HttpException {
		HttpGet httpGet = new HttpGet(url);
		if (params != null) {
			httpGet.setParams(params);
		}
		return exec(httpGet, new BasicResponseHandler());
	}

	public static String post(String url, HttpParams params) throws HttpException {
		HttpPost httpPost = new HttpPost(url);
		if (params != null) {
			httpPost.setParams(params);
		}
		return exec(httpPost, new BasicResponseHandler());
	}

	/**
	 * http异常
	 * 
	 * @author yang.hao
	 * @since 2011-8-31 下午9:12:06
	 */
	public static class HttpException extends Exception {
		private static final long serialVersionUID = 1L;

		public HttpException(String message, Throwable cause) {
			super(message, cause);
		}

		public HttpException(String message) {
			super(message);
		}

		public HttpException(Throwable cause) {
			super(cause);
		}
	}
}
