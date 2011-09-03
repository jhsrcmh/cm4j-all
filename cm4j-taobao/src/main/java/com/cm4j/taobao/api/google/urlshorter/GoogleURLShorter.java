package com.cm4j.taobao.api.google.urlshorter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.common.APIConstants;
import com.cm4j.taobao.utils.HttpClientUtils.HttpException;

public class GoogleURLShorter {

	private static final Logger logger = LoggerFactory.getLogger(GoogleURLShorter.class);

	/**
	 * google提交URL
	 */
	private static final String submit_url = APIConstants.getGoogleShorterURL();

	public static int request_timeout = 1500;

	/**
	 * 还原原网址 - GET
	 * 
	 * @param shortUrl
	 * @return
	 * @throws HttpException
	 */
	public static String longen(String shortUrl) throws HttpException {
		try {
			String longedJson = httpRequest(submit_url + "&shortUrl=" + shortUrl, "GET", request_timeout);
			GoogleURLShorterAnalytics fromJson = APICaller.jsonBinder.fromJson(longedJson,
					GoogleURLShorterAnalytics.class);
			return fromJson.getLongUrl();
		} catch (Exception e) {
			throw new HttpException(e);
		}
	}

	/**
	 * 缩短网址 - POST
	 * 
	 * @param longUrl
	 * @return
	 * @throws HttpException
	 */
	public static String shorten(String longUrl) throws HttpException {
		try {
			GoogleURLShorterAnalytics pojo = new GoogleURLShorterAnalytics();
			pojo.setLongUrl(longUrl);
			String shortedJson = invoke(APICaller.jsonBinder.toJson(pojo), request_timeout);
			GoogleURLShorterAnalytics fromJson = APICaller.jsonBinder.fromJson(shortedJson,
					GoogleURLShorterAnalytics.class);
			return fromJson.getId();
		} catch (Exception e) {
			throw new HttpException(e);
		}
	}

	/**
	 * 注意这里调用goole的api，这里传送数据的方式是直接写到留里面，而不是key=value方式
	 * 
	 * @param jsonParams
	 * @return
	 */
	public static String invoke(String jsonParams, int timeout) {
		StringBuilder builder = new StringBuilder();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(submit_url).openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(timeout);
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
			if (StringUtils.isNotBlank(jsonParams)) {
				osw.write(jsonParams);
			}
			osw.flush();
			osw.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			return resolverResponse(br);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return builder.toString();
	}

	/**
	 * 发送http 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param method
	 *            请求方法，支持： GET POST HEAD OPTIONS PUT DELETE TRACE
	 * @param timeout
	 *            请求超时时间
	 * @return 响应结果
	 */
	public static String httpRequest(String url, String method, int timeout) {
		HttpURLConnection connection = null;
		logger.debug("http [{}]:{}", method, url);
		try {
			URL u = new URL(url);
			connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod(method);
			connection.setConnectTimeout(timeout);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			return resolverResponse(reader);
		} catch (Exception e) {
			logger.error("http [{}] fail:{}", method, url);
			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
		} finally {
			// 断开连接
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	private static String resolverResponse(BufferedReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		String result = StringUtils.substringBeforeLast(sb.toString(), "\n");
		reader.close();
		return result;
	}
}
