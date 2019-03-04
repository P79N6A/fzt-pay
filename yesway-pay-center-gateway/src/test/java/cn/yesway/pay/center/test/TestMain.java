package cn.yesway.pay.center.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;

public class TestMain {

	
	public static void main(String[] args) throws InterruptedException, ParseException {
		/*String code = "PK+sdV/3LFLyzMOrxPK5LaNnMdgKU3YjW9Ew0+ThbEX09Swcki8FRXZW8QOnXxzw55yMy0YEKmucSIhnNcOYixCY0kyQTUHALgO4a1oK0GFq+Jh+hGd9tRL+9FSu2do5VOmvSZq1GUj7B/9VOUF2VGZKrxCV4p8YX9vxcqE1ET0Jon4u/U0mBPjZCQKsBsE3V4fMeL/4MFasqPDVpmo9RvLrpwHC0uVW06cOb0jPaQQaqwVmOLE+h28AKIOS9RhD2UAEmRJ072LEYnr4Gq5Kv+lMLOqU2/KC9RxAqqvFqWEq6eOnzW5Z4TKckz6whaeEUluHY9Ow+Qs9BnM/fHbuvgKCxz+Zbrf7+tAM/WH1z6vZ6fMIZsUJ3BZSlpzJLcFjUqICOYAiIvtAjRvSdc7u+uLWymakyD/YbMAiV71gNjsmb8Nvmf8cA5RJmYW/q4jutqWmlqV12JaQgxDMjftqwqdju9w+deQ/0dh+ymES9eau35jnteYu3krTgI5jPcNkQnCvNXfbyYtR8Wti1fRVbkbEeWtgXV5UF687VLAZihdQBu9yOIH16wTErmCEhWIElJ6+xoCKjc1i6uhCtXJ9La9mZrDomg4fwZeau1Q4xWljSfenjmYClbmcR2din02wzZUT96F+LfhJ3ROT7Eux8uO4V9Kx/WmvWwjlGChIyZc=";
		String aesSecretKey="1234567890123456";
		String sdata = AESSecurityUtils.AESDncode(aesSecretKey, code);
		System.out.println(sdata);*/
		
		/*JSONArray arr = new JSONArray();
		arr.add("{\"body\":\"苹果手机\",\"goods_category\":\"123456\",\"price\":528800,\"wxpay_goods_id\":\"1001\",\"goods_id\":\"iphone6s_16G\",\"goods_name\":\"iPhone6s16G\",\"quantity\":1}");
        arr.add("{\"body\":\"苹果手机\",\"goods_category\":\"123789\",\"price\":608800,\"wxpay_goods_id\":\"1002\",\"goods_id\":\"iphone6s_32G\",\"goods_name\":\"iPhone6s32G\",\"quantity\":1}");
        System.out.println(arr.toString());*/
		
//		String s1="body购买手机goods_detail[{\"quantity\":1,\"price\":528800,\"wxpay_goods_id\":\"1001\",\"goods_category\":\"123456\",\"goods_name\":\"iPhone6s16G\",\"body\":\"苹果手机\",\"goods_id\":\"iphone6s_16G\"},{\"quantity\":1,\"price\":608800,\"wxpay_goods_id\":\"1002\",\"goods_category\":\"123789\",\"goods_name\":\"iPhone6s32G\",\"body\":\"苹果手机\",\"goods_id\":\"iphone6s_32G\"}]out_trade_no1000201702141333pay_tool_type2spbill_create_ip14.23.150.211subject购买手机total_amount0.01trade_typeAPP";
//		String s2="body购买手机goods_detail[{\"quantity\":1,\"price\":528800,\"wxpay_goods_id\":\"1001\",\"goods_category\":\"123456\",\"goods_name\":\"iPhone6s16G\",\"body\":\"苹果手机\",\"goods_id\":\"iphone6s_16G\"},{\"quantity\":1,\"price\":608800,\"wxpay_goods_id\":\"1002\",\"goods_category\":\"123789\",\"goods_name\":\"iPhone6s32G\",\"body\":\"苹果手机\",\"goods_id\":\"iphone6s_32G\"}]out_trade_no1000201702141333pay_tool_type2spbill_create_ip14.23.150.211subject购买手机total_amount0.01trade_typeAPP";
//		System.out.println(s1.equals(s2));
		
//		System.out.println("D508306C16B5038C5B82008B139B4C7D".length());
		
		/*SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		Date d1 = format.parse("20170426130000");
		Date d2 = format.parse("20170426130500");
		long c = d2.getTime() - d1.getTime();
		c = c/1000/60;
		System.out.println("1=="+c);
		
		if(c < 5L){
			System.out.println("2=="+c);
		}*/
		
		/*Map<String, Object> returnMap = new HashMap<String,Object>();
		System.out.println(returnMap.size());*/
		
		/*long sysDate = System.currentTimeMillis();
		System.out.println(sysDate);
		 
		System.out.println(new Date().getTime());*/
		
		/*int n = RandomUtils.nextInt(5);
		System.out.println(n);*/
		
//		int i=(int)(Math.random()*900)+100;
//		System.out.println(i);
		
		/*double r = Math.random();
		System.out.println(r);
		System.out.println(r*900);
		System.out.println((int)(r*900));
		System.out.println((int)(r*900)+100);*/
		
		DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
		System.out.println();
		
//		StringUtils.leftPad(str, size)
		
	}

}
