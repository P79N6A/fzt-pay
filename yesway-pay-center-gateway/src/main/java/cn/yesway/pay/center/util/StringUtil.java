package cn.yesway.pay.center.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.yesway.pay.center.exception.BusiException;
import cn.yesway.pay.center.util.sign.MD5;



public class StringUtil {
    
    
    private static Logger logger = Logger.getLogger(StringUtil.class);
	/**
	 * 文件名加后缀
	 * @param filePathString
	 * @param addString
	 * @return
	 */
	public String addForFilePath(String filePathString,String addString){
		int dotaIndex=filePathString.lastIndexOf(".");
		if(dotaIndex!=-1){
			return filePathString.substring(0,dotaIndex)+addString+filePathString.substring(dotaIndex);
		}else {
			return filePathString+addString;
		}		
	}
	public static String getCapitalize(String srcString,String addString){
		return addString+(srcString.charAt(0)+"").toUpperCase()+srcString.substring(1);
	}
	public static String getNoCapitalize(String srcString,String addString){
		return addString+(srcString.charAt(0)+"").toLowerCase()+srcString.substring(1);
	}
	public static String addWhenUpper(String srcString,String addString){
		StringBuilder returnStringBuilder=new StringBuilder();
		for (int i = 0; i < srcString.length(); i++) {
			char c=srcString.charAt(i);
			if (Character.isUpperCase(c)) {
				returnStringBuilder.append(addString+c);
			}else {
				returnStringBuilder.append(c);
			}
		}
		return returnStringBuilder.toString();
	}
	/**
	 * 获取一个字符串首字母大写的格式
	 * 例如：输入adminInfo将返回AdminInfo
	 * @param srcString
	 * @return
	 */
	public static String getFirstUp(String srcString){
		return (srcString.charAt(0)+"").toUpperCase()+srcString.substring(1);
	}
	/**
	 * 获取一个字符串首字母小写的格式
	 * 例如：输入AdminInfo将返回adminInfo
	 * @param srcString
	 * @return
	 */
	public static String getFirstLower(String srcString){
		return (srcString.charAt(0)+"").toLowerCase()+srcString.substring(1);
	}
	/**
	 * 判断一个字符串是否为空，如果为空返回true,否则返回false.
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		boolean flag = false;
		if(str==null || "".equals(str.trim())){
			flag = true;
		}
		return flag;
	}
	/**
	 * 该方法主要用于把key1=value&key2=value2字符串转化成map
	 * @param param
	 * @return
	 */
	public static Map<String, String> transformParam(String param){
		Map<String, String> paramMap=new HashMap<String, String>();
		try {
			if(!isBlank(param)){
				String[] arr=param.split("&");
				for (int i=0;i<arr.length;i++) {
					String[] array=arr[i].split("=");
					if(array.length>0){
						if(array.length==2){
							paramMap.put(java.net.URLDecoder.decode(array[0],   "utf-8"), java.net.URLDecoder.decode(array[1],   "utf-8"));
						}else{
							paramMap.put(java.net.URLDecoder.decode(array[0],   "utf-8"), "");
						}
					}
				}					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramMap;
	}
	/**
	 * 如果字符串为null，抛异常
	 */
	public static String trimOrNullException(String source,String msg){
		if (source==null) {
			throw new BusiException("trimOrNullException(null)【"+msg+"】");
		}else {
			return source.trim();
		}
	}
	/**
	 * 如果字符串为null或空，抛异常
	 */
	public static String trimOrNullAndBlankException(String source,String msg){
		if (source==null||"".equals(source)) {
			throw new BusiException("trimOrNullAndBlankException(null||\"\")【"+msg+"】");
		}else {
			return source.trim();
		}
	}
	
	/**
	 * 转换编码
	 * - 处理乱码问题
	 * @return str为空、转换出错返回null
	 */
	public static String decode(String str, String fromCode, String toCode){
		if(isBlank(str)){
			return null;
		}else{
			try {
				return new String(str.getBytes(fromCode), toCode);
				
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
	}
	
	/**
	 * 去掉字符串开头的数字
	*/
	public static String getNumbers(String source) {
		if (source==null||"".equals(source)) {
			return "";
		}else {
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(source);
			while (matcher.find()) {
				return matcher.group(0);
			}
		}		
		return "";
	}
	/**
	 * 检查参数
	 * @param param
	 * @param paramMd5
	 * @param key
	 * @return
	 * @author LiangMeng
	 */
	public static boolean checkParam(String param,String paramMd5,String key){
	    String paramMd5New = MD5.sign(param, key,"utf-8");
	    return paramMd5New.equals(paramMd5);
	}
	/**
	 * 参数加密
	 * @param param
	 * @param key
	 * @return
	 * @author LiangMeng
	 */
	public static String encodeParam(String param,String key){
        return MD5.sign(param, key,"utf-8");
    }
	
	
	/**
	 * 微信浏览器提示
	 * @return
	 * @author zhangkq
	 */
	public static String WEItext(){
	    String text ="亲，因为微信屏蔽支付宝支付页面的关系，通过微信浏览器不能直接跳转到您的支付宝，请按以下方式进行操作，您辛苦了......"
	                        +"点击“确认支付”—复制“支付宝链接“—打开手机自带或其他浏览器—粘贴”支付宝链接“—进行支付";
	    return text;
	}
	
	
	public static String joinMapValue(Map<String, String> map, String connector) {
        StringBuffer b = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            b.append(entry.getKey());
            b.append('=');
            if (entry.getValue() != null) {
                b.append(entry.getValue());
            }
            b.append(connector);
        }
        return b.toString();
    }
	
	/**
	 * 快捷加密
	 * @param str
	 * @return
	 * @author zhangkq
	 * @throws UnsupportedEncodingException 
	 */
	public static String kuaijieMD5(String str,String key) {
	    StringBuilder sb = new StringBuilder();
	
	    str = str + "key=" +key;
	    logger.info("加密前字符串："+str);
        try {
            byte[] temp = str.getBytes("UTF-8");
            String pubString=new String(temp);
            MessageDigest code = MessageDigest.getInstance("MD5");
            code.update(pubString.getBytes());
            byte[] bs = code.digest();
            for (int i = 0; i < bs.length; i++) {
                int v = bs[i] & 0xFF;
                if (v < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(v));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        logger.info("加密后字符串："+sb);
        return sb.toString();
    }
	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 } 
	/**
	 * 获取将参数按字典序拼接的字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 *            注：参数值为空的参数不进行排序
	 * @return 拼接后字符串 
	 */
	public static String linkString(Map<String, Object> params,String symbol) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = (String)params.get(key);
			if(value !=null && !"".equals(value)){
				stringBuilder = stringBuilder.append(symbol).append(key + "=" + value);
			}
		}
		return (stringBuilder.toString()).length()>0?(stringBuilder.toString()).substring(1):stringBuilder.toString();
	}
}
