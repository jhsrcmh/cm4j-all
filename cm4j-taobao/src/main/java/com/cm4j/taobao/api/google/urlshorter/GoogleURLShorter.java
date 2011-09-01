package com.cm4j.taobao.api.google.urlshorter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.cm4j.taobao.api.common.APIConstants;
import com.cm4j.taobao.utils.HttpClientUtils;
import com.cm4j.taobao.utils.HttpClientUtils.HttpException;

public class GoogleURLShorter {

	/**
	 * google提交URL
	 */
	private static final String submit_url = APIConstants.getGoogleShorterURL();

	/**
	 * 还原原网址 - GET
	 * 
	 * @param shortUrl
	 * @return
	 * @throws HttpException
	 */
	public static String longen(String shortUrl) throws HttpException {
		HttpParams params = new BasicHttpParams();
		params.setParameter("shortUrl", shortUrl);
		return HttpClientUtils.get(submit_url, params);
	}

	public static String shorten(String longUrl) {
		String shortUrl = "";
		StringBuilder builder = new StringBuilder();
		try {
			URLConnection conn = new URL(submit_url).openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
			osw.write("{\"longUrl\":\"" + longUrl + "\"}");
			osw.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			osw.close();
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return builder.toString();
	}
	
//	public static class GoogleShorter

}
