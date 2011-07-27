package com.cm4j.taobao.api.identity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

import com.taobao.api.internal.util.codec.Base64;

/**
 * 身份认证 - 基于回调函数
 * 
 * @author yang.hao
 * @since 2011-7-25 上午09:51:42
 * 
 */
@SuppressWarnings("restriction")
public class IdentityContext {

	/**
	 * 验证TOP回调地址的签名是否合法。要求所有参数均为已URL反编码的。
	 * 
	 * @param topParams
	 *            TOP私有参数（未经BASE64解密）
	 * @param topSession
	 *            TOP私有会话码
	 * @param topSign
	 *            TOP回调签名
	 * @param appKey
	 *            应用公钥
	 * @param appSecret
	 *            应用密钥
	 * @param appSecret
	 *            应用密钥
	 * @return 验证成功返回true，否则返回false
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static boolean verifyTopResponse(String topParams, String topSession, String topSign, String appKey,
			String appSecret) throws NoSuchAlgorithmException, IOException {
		StringBuilder result = new StringBuilder();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		result.append(appKey).append(topParams).append(topSession).append(appSecret);
		byte[] bytes = md5.digest(result.toString().getBytes("UTF-8"));
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(bytes).equals(topSign);
	}

	/**
	 * 解析top_parameters
	 * 
	 * @param top_parameters
	 * @param keyName 根据此keyName获取key的值
	 * @return
	 */
	public static String resolveParameters(String top_parameters, String keyName) {
		Map<String, String> map = convertBase64StringtoMap(top_parameters);
		return map.get(keyName);
	}

	/**
	 * 把经过BASE64编码的字符串转换为Map对象
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private static Map<String, String> convertBase64StringtoMap(String str) {
		if (str == null)
			return null;
		String keyvalues = null;
		try {
			keyvalues = new String(Base64.decodeBase64(URLDecoder.decode(str, "utf-8").getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] keyvalueArray = keyvalues.split("\\&");
		Map<String, String> map = new HashMap<String, String>();
		for (String keyvalue : keyvalueArray) {
			String[] s = StringUtils.split(keyvalue, "\\=");
			if (s == null || s.length != 2)
				return null;
			map.put(s[0], s[1]);
		}
		return map;
	}
}
