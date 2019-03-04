package cn.yesway.pay.center.test;

import java.util.HashMap;
import java.util.Map;

import cn.yesway.pay.center.exception.InvalidParamException;
import cn.yesway.pay.center.exception.handle.ExceptionHandlerFactory;

import com.alibaba.fastjson.JSON;

public class TestException {

	public static void main(String[] args) {
		boolean b = testExpt();
		System.out.println(b);
	}

	public static boolean testExpt(){
		Map<String, Object> map = new HashMap<String, Object>();
		String n = "3a";
			
		try {
			if(n != "@#$@34"){
				System.out.println("valid");
				throw new InvalidParamException("无效的参数");
			}
			if(n == "2a"){
				System.out.println("valid");
				throw new InvalidParamException("无效的参数");
			}
			System.out.println("addsxd234234&^%$#@");
		} catch (Exception e) {			
			map.put("messageheader", ExceptionHandlerFactory.getHandler().process(e));
			String responseStr = JSON.toJSONString(map);
 			System.out.println("response_content: " + responseStr);
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
