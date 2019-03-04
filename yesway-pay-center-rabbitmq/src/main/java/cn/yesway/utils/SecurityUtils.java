package cn.yesway.utils;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;


public class SecurityUtils {
	/**
	 * 对字符串md5加密
	 *
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }	    
	}
	public static String getMd5Byte32(String plainText) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(plainText.getBytes());  
            byte b[] = md.digest();  
  
            int i;  
  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32位加密  
            return buf.toString();  
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
	public static Boolean rightSign(HttpServletRequest request, String userSecurityKey) throws Exception {
		String key = request.getParameter("key");
		String serviceKey = request.getParameter("servicekey");
		String timestamp = request.getParameter("timestamp");
		String sign = URLDecoder.decode(request.getParameter("sign"),"utf-8");

		Map<String, String> params = new HashMap<String, String>();
		params.put("key", key);
		params.put("servicekey", serviceKey);
		params.put("timestamp", timestamp);

		String mySign = new String(Base64.encodeBase64(HmacSHA1Encrypt(createLinkString(params), userSecurityKey)));

		return sign.equals(URLDecoder.decode(mySign, "utf-8"));
	}

	/**
	 * 获取将参数按字典序拼接的字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 *            注：参数值为空的参数不进行排序
	 * @return 拼接后字符串 
	 */
	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if(value !=null && !"".equals(value)){
				stringBuilder = stringBuilder.append(key + "" + value);
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptText
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
		byte[] data = encryptKey.getBytes("UTF-8");
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance("HmacSHA1");
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);

		byte[] text = encryptText.getBytes("UTF-8");
		// 完成 Mac 操作
		return mac.doFinal(text);
	}
	
	public static void main(String[] args) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		params.put("out_trade_no", "1000201702141333");
		params.put("total_amount", "88.88");
		params.put("pay_tool_type", "2");
		params.put("subject", "购买手机");
		params.put("body", "购买手机");
		params.put("goods_detail", "[{\"goods_id\":\"iphone6s_16G\",\"wxpay_goods_id\":\"1001\",\"goods_name\":\"iPhone6s 16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s 32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]");
		params.put("spbill_create_ip", "14.23.150.211");
		params.put("trade_type", "APP");
		System.out.println(createLinkString(params));
		
//		String mySign = new String(Base64.encodeBase64(HmacSHA1Encrypt(createLinkString(params), "xiaomign")));
//		System.out.println(mySign);
	}
}
