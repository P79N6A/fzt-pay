package cn.yesway.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import org.springframework.util.Base64Utils;

//import android.util.Base64;


/**
 * @author 针对Android和ios两种方式的加解密方法
 *  2017年4月17日上午11:39:00
 *  SecurityUtil_RSA
 */
public class SecurityUtil_AES { // 加密
	public static String encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());
		//return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
		//return 	new String(Base64.encode(encrypted, Base64.NO_WRAP));// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		return 	new String(Base64Utils.encode(encrypted));
	}

	// 解密
	public static String decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			
			//byte[] encrypted1 = Base64.decode(sSrc,Base64.NO_WRAP);// 先用base64解密
			//byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			byte[] encrypted1 = Base64Utils.decode(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 此处使用AES-128-ECB加密模式，key需要为16位。
		 */
		String cKey = "1234567890123456";
		// 需要加密的字串
		String cSrc = "{\"body\":{\"code\":\"067014\",\"engine\":\"123\",\"imei\":\"867223020015608\",\"lpn\":\"123\",\"mobilePhone\":\"13641300741\",\"name\":\"饭饭\",\"vin\":\"12345678901234567\",\"companyCode\":\"beiqixinnengyuan\",\"username\":\"ryan\"},\"header\":{\"code\":\"R1000\"}}";
		System.out.println(cSrc);
		// 加密
		System.out.println(System.currentTimeMillis());
		String enString = SecurityUtil_AES.encrypt(cSrc, cKey);
		System.out.println("加密后的字串是：" + enString);

		System.out.println(System.currentTimeMillis());

		// 解密
		String DeString = SecurityUtil_AES.decrypt(enString, cKey);
		System.out.println("解密后的字串是：" + DeString);
		System.out.println(System.currentTimeMillis());
	}
}
