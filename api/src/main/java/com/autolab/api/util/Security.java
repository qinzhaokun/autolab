/*
 * Copyright (c) 2013. Secmon Inc. All rights reserved.
 */

package com.autolab.api.util;

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Security {

	//MD5加密
	public static String encodeByMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String encodeByMD5(String str, Integer len) {
		return encodeByMD5(str).substring(0, len);
	}

	//Base64加密
	public static String encodeByBase64(String src) {
		return Base64.encodeBase64URLSafeString(src.getBytes());
	}

	//base64解密
	public static String decodeFromBase64(String src) {
		return new String(Base64.decodeBase64(src));
	}

	//两次base64加密
	public static String encodeByDoubleBase64(String src) {
		return encodeByBase64(encodeByBase64(src));
	}

	//两次base64解密
	public static String decodeFromDoubleBase64(String src) {
		String str1 = decodeFromBase64(src);
		return decodeFromBase64(str1);
	}


}
