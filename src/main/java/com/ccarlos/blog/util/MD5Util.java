package com.ccarlos.blog.util;

import com.ccarlos.blog.common.constant.RabbitMQConst;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description: MD5加密工具类
 * @author: Created by ccarlos
 * @date: 2019/3/31 17:17
 */
@Slf4j
public class MD5Util {

	// 16进制数组
	private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

	/**
	 * @description: byte数组转换成十六进制字符串
	 * @author: ccarlos
	 * @date: 2019/3/31 17:18
	 * @param: b byte数组
	 * @return: java.lang.String
	 */
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			stringBuffer.append(byteToHexString(b[i]));
		return stringBuffer.toString();
	}

	/**
	 * @description: byte字节转换成十六进制字符串
	 * @author: ccarlos
	 * @date: 2019/3/31 17:21
	 * @param: b byte字节
	 * @return: java.lang.String
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * @description: 加密，返回大写MD5
	 * @author: ccarlos
	 * @date: 2019/3/31 17:25
	 * @param: origin 待加密字符串
	 * @param: charsetname 编码方式
	 * @return: java.lang.String
	 */
	private static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception e) {
			log.error("密码加密发生异常，异常原因:{}", e);
		}
		return resultString.toUpperCase();
	}

	/**
	 * @description: 制定编码方式，进行加密
	 * @author: ccarlos
	 * @date: 2019/3/31 17:30
	 * @param: origin 待加密字符串
	 * @return: java.lang.String
	 */
	public static String MD5EncodeUtf8(String origin) {
		return MD5Encode(origin, "utf-8");
	}

	/**
	 * @description: 获取输入流的MD5值
	 * @author: ccarlos
	 * @date: 2019/5/24 12:54
	 * @param: is 输入流
	 * @return: java.lang.String
	 */
	public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
		StringBuffer stringBuffer = new StringBuffer();
		MessageDigest messageDigest = MessageDigest.getInstance(RabbitMQConst.MD5_ENCRYPTION_NAME);
		byte[] bytes = new byte[1024];
		int next = 0;
		while ((next = is.read(bytes)) != -1) {
			messageDigest.update(bytes, 0, next);
		}
		byte[] mdBytes = messageDigest.digest();

		for (int i = 0; i < mdBytes.length; i++) {
			stringBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}
}
