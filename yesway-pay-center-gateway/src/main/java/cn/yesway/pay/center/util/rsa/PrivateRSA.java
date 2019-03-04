
package cn.yesway.pay.center.util.rsa;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;




/**
 * @author ghk
 *  2017年2月10日下午2:55:38
 *  PrivateRSA私钥
 */
public class PrivateRSA {
	//String strPriKey = cn.yesway.pay.center.util.AppConfig.getParameter("token");
//	private static String strPriKey = cn.yesway.pay.center.util.AppConfig.getParameter("strPriKey");
	private static String strPriKey2="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJjlW8RNz7z/HRFOYyBHXoe2M/SO"
									+"uykfgZ6wOS8aUxHmNRB2d5nhF22IDDFV9+3ogkSoN28VEnby++uFjy6YakU/VWeVWRABWi4HQoXh"
									+"wyBKaySg5aPUoVUTwLhX+Ei+HJT7wZqzqTAKiZ7Qq2PBJHBECYT+F4i8de1AYT5A5HDxAgMBAAEC"
									+"gYAOqahESiYK/Pg/qaHN/aouH6YZ5PpD9laK7mF9h+vBgq9CFepgYnrJsUtiC9mMAklD5lHyoDW2"
									+"HJVWP+e8UzeVqAKGWWYDoezCBfS3xqq+9nE8a/t3UP0QASl3lbkgWmUrZStJR9F7+99DC5nN1H8Q"
									+"lftPBINgOC/dmnu8+DPc5QJBAOoHO2mwM0Vl1hn/oBfryG/A6/OSTtYBdgdrAOgMRFeQsop2cy0f"
									+"SUNxUAOXLV+RAxUzy33Qr7gcYGhrDRzYqOsCQQCnQCVphD41R2XeZ5ukBU3JJGHEQxvJ3HsKzvxZ"
									+"CtkfPosPD6rhtMzbkrXmLQqVBkl/+iOS/1VZO0t+040oFdaTAkAHneakIPT6PD9Ep1o5jRX/9SJ4"
									+"0fk44+FIioYEB2ouA2qcMRC8ljkXNdfp1gDHDHwyM3ZbCPC/KEVHALzzhGWzAkEAkGzrIagJ1BZY"
									+"mGqd01ClMCmTp1hQGcukENxWu0mL3tgyWworhQaM1JtnKmIAvlmUhMfrilelSw3SDq3+OfxJswJB"
									+"AJzdwjQAPesO2Il3wRW8LgWyHLNaQmkymT8J4D0zqJaqwrc6OhfXud7Ojo3YCNMx2IlR7eAhjaVA"
									+"XcwR4QK1g5g="; //= FileConfig.private_key;
										
	private static RSAPrivateKey privateKey = getPrivateKey(strPriKey2);
	private static PrivateRSA instance = null;
	
	private PrivateRSA(){
		
	}
	
	public static PrivateRSA getInstance(){
		if(instance==null){
			instance = new PrivateRSA();
		}
		return instance;
	}
	
	/** 
     * 私钥解密
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public  String decryptByPrivateKey(String data)  
            throws Exception {  
    	Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey); 
        return decrypt(data,cipher);  
    }

	 
    public  String decryptByPrivateKey(String data,RSAPrivateKey privateKey)  
    		throws Exception {  
    	Cipher cipher = Cipher.getInstance("RSA");  
    	cipher.init(Cipher.DECRYPT_MODE, privateKey);  
    	return decrypt(data,cipher);  
    }   
    private String decrypt(String data,Cipher cipher) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IOException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {        
		int key_len = privateKey.getModulus().bitLength() / 8;  
		byte[] base64 =(new BASE64Decoder()).decodeBuffer(data);
		
		String ming = "";  
		byte[][] arrays = splitArray(base64, key_len);  
		for(byte[] arr : arrays){  
		    ming += new String(cipher.doFinal(arr));  
		}
		
		ming = java.net.URLDecoder.decode(ming, "UTF-8");        
		return ming;
	}  
    /** 
     * 私钥加密
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public  String encryptByPrivateKey(String data)  
            throws Exception {  
    	
    	data = java.net.URLEncoder.encode(data,"UTF-8");
    	
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
         
        int key_len = privateKey.getModulus().bitLength() / 8;  
       
        String[] datas = splitString(data, key_len - 11);  
        String mi = "";  
        
        for (String s : datas) {  
        	mi += (new BASE64Encoder()).encode(cipher.doFinal(s.getBytes()));
        }  
        return mi;  
    } 
    
    /** 
     * 拆分字符串 
     */  
    public static String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    }  
    /** 
     *拆分字节数组
     */  
    public static byte[][] splitArray(byte[] data,int len){  
        int x = data.length / len;  
        int y = data.length % len;  
        int z = 0;  
        if(y!=0){  
            z = 1;  
        }  
        byte[][] arrays = new byte[x+z][];  
        byte[] arr;  
        for(int i=0; i<x+z; i++){  
            arr = new byte[len];  
            if(i==x+z-1 && y!=0){  
                System.arraycopy(data, i*len, arr, 0, y);  
            }else{  
                System.arraycopy(data, i*len, arr, 0, len);  
            }  
            arrays[i] = arr;  
        }  
        return arrays;  
    } 
	
	/**
     * 获得公钥
     * @param key 公钥字符串
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String key){
    	try{
    		byte[] keyBytes;
            keyBytes = (new BASE64Decoder()).decodeBuffer(key); //(new BASE64Decoder()).decodeBuffer(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);
            return publicKey;
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	} 
    }
    /**
     * 获得私钥
     * @param key 私钥字符串
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String key)  {
    	try{
    		byte[] keyBytes;
            keyBytes = (new BASE64Decoder()).decodeBuffer(key); //(new BASE64Decoder()).decodeBuffer(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
            return privateKey;
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
    }
}

