package cn.yesway.pay.center.util.sha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncrypSHA {
	 /** 
     * 全局数组 
     */  
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",  
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };  
	public static String eccrypt(String info,String shaType) throws NoSuchAlgorithmException{  
        MessageDigest sha = MessageDigest.getInstance(shaType);  
        byte[] srcBytes = info.getBytes();  
        //使用srcBytes更新摘要  
        sha.update(srcBytes);  
        //完成哈希计算，得到result  
        byte[] resultBytes = sha.digest();  
        return byteArrayToHexString(resultBytes);  
    }  
    public static String eccryptSHA1(String info) throws NoSuchAlgorithmException{  
       return eccrypt(info,"SHA1");  
    }  
    public static String eccryptSHA256(String info) throws NoSuchAlgorithmException{  
        return eccrypt(info,"SHA-256");  
    }  
    public static String eccryptSHA384(String info) throws NoSuchAlgorithmException{  
        return eccrypt(info,"SHA-384");  
    }  
    public static String eccryptSHA512(String info) throws NoSuchAlgorithmException{  
        return eccrypt(info,"SHA-512");  
    }  
    public static void main(String[] args) throws NoSuchAlgorithmException {  
        String msg ="欢迎光临JerryVon的博客";  
        EncrypSHA sha = new EncrypSHA();  
        System.out.println("明文是：" + msg);  
//        System.out.println("密文是：" + byteArrayToHexString(sha.eccryptSHA1(msg)));  
//        System.out.println("密文是：" + byteArrayToHexString(sha.eccryptSHA256(msg)));  
//        System.out.println("密文是：" + byteArrayToHexString(sha.eccryptSHA384(msg)));  
//        System.out.println("密文是：" + byteArrayToHexString(sha.eccryptSHA512(msg)));  
    } 
    private static String byteArrayToHexString(byte[] bytes) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < bytes.length; i++) {  
            sb.append(byteToHexString(bytes[i]));  
        }  
        return sb.toString();  
    } 
    /** 
     * 将一个字节转化成十六进制形式的字符串 
     * @param b 字节数组 
     * @return 字符串 
     */  
    private static String byteToHexString(byte b) {  
        int ret = b;  
        //System.out.println("ret = " + ret);  
        if (ret < 0) {  
            ret += 256;  
        }  
        int m = ret / 16;  
        int n = ret % 16;  
        return hexDigits[m] + hexDigits[n];  
    }
   
}

