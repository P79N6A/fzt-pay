package cn.yesway.pay.center.util;


import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;

public class SerialNumberGenerator {
	/** 
     * 锁对象，可以为任意对象 
     */  
    private static Object lockObj = "locker";  
    
	private static String REGION_CODE_DEFAULT = "00";
	/** 
     * 编号生成计数器 
     */  
    private static long numCount = 0L;  
    /** 
     * 每毫秒生成OEM编号数量最大值 
     */  
    private static long OEMMaxPerMSECSize=1000L;
    
    /**
     * 每毫秒生成商户编号数量最大值 
     */
    private static long MchMaxPerMSECSize=100000L;
	/**
	 * 获取订单编号<br>
	 * 订单编号规则： OEM编号（4位）+商户编号（6位）+地区编码+（2位）+yyyyMMddHHmmssSSS+随机数（3位）
	 * @param oemCode OEM编码
	 * @param mchCode 商户编码
	 * @param regionCode 地区编码 如果为空的话默认为00
	 * @return String 订单编号
	 */
	public static String getOrderCode(String oemCode,String mchCode,String regionCode){
		if(StringUtils.isBlank(oemCode)|| StringUtils.isBlank(mchCode)){
			return null;
		}
		if(StringUtils.isBlank(regionCode)){
			regionCode = REGION_CODE_DEFAULT;
		}
		String dateStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
		int randomStr=(int)(Math.random()*900)+100;
		String serial = oemCode+mchCode+regionCode+dateStr+randomStr;
		return serial;
	}
	/**
	 * 获取OEM编号<br>
	 * 编号规则：4位数字
	 * @return String
	 */
	public static String getOEMCode(){
		// 最终生成的OEMN编号  
		String finOemNum = "";  
		synchronized (lockObj) {  
            // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万  
            if (numCount >= OEMMaxPerMSECSize) {  
            	numCount = 0L;  
            }  
            //组装OEMN编号  
            finOemNum=OEMMaxPerMSECSize +numCount+"";  
            numCount++;  
            return finOemNum;
        }  
		
	}
	/**
	 * 获取商户编号<br>
	 * 编号规则：6位数字
	 * @return
	 */
	public static String getMchCode(){
		
		// 最终生成的OEMN编号  
		String finMchNum = "";  
		synchronized (lockObj) {  
            // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万  
            if (numCount >= MchMaxPerMSECSize) {  
            	numCount = 0L;  
            }  
            //组装OEMN编号  
            finMchNum=MchMaxPerMSECSize +numCount+"";  
            numCount++;  
            return finMchNum;
        } 
	}
	public static void main(String[] args) {
		String mch = SerialNumberGenerator.getMchCode();
		String oem = SerialNumberGenerator.getOEMCode();
		
		System.out.println(SerialNumberGenerator.getOrderCode(oem, mch, null));
		
//		testGetOEMNumber();
//		testGetMchNumber();
		
	}
	public static void testGetMchNumber(){
		// 测试多线程调用Mch编号生成工具  
        try {  
            for (int i = 0; i < 20000; i++) {  
                Thread t1 = new Thread(new Runnable() {  
                    public void run() {  
//                        MakeOrderNum makeOrder = new MakeOrderNum();  
                    	System.out.println(SerialNumberGenerator.getMchCode());  
                    }  
                }, "at" + i);  
                t1.start();  

                Thread t2 = new Thread(new Runnable() {  
                    public void run() {  
//                        MakeOrderNum makeOrder = new MakeOrderNum();  
                    	System.out.println(SerialNumberGenerator.getMchCode());  
                    }  
                }, "bt" + i);  
                t2.start();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	public static void testGetOEMNumber(){
		 // 测试多线程调用OEM编号生成工具  
        try {  
            for (int i = 0; i < 200; i++) {  
                Thread t1 = new Thread(new Runnable() {  
                    public void run() {  
//                        MakeOrderNum makeOrder = new MakeOrderNum();  
                    	System.out.println(SerialNumberGenerator.getOEMCode());  
                    }  
                }, "at" + i);  
                t1.start();  

                Thread t2 = new Thread(new Runnable() {  
                    public void run() {  
//                        MakeOrderNum makeOrder = new MakeOrderNum();  
                    	System.out.println(SerialNumberGenerator.getOEMCode());  
                    }  
                }, "bt" + i);  
                t2.start();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

}
