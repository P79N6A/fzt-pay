package cn.yesway.pay.center.test.weixin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.dom4j.DocumentException;

import cn.yesway.pay.center.util.HttpUtil;
import cn.yesway.pay.center.util.SecurityUtils;
import cn.yesway.pay.center.util.StringUtil;
import cn.yesway.pay.center.util.XMLUtil;

public class TestWeiXinAPI {

	/**
	 * 微信统一下单接口链接
	 */
	private final static String WEIXIN_UNIFIE_DORDER_URL ="https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static void main(String[] args) throws DocumentException {
		unifiedOrder();

	}
	public static void unifiedOrder() throws DocumentException{		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", "wxcc87001da05f04a0");
		params.put("mch_id", "1422167202");		
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));		
		params.put("body", "购买手机");
		params.put("detail", "{\"goods_detail\":[{\"goods_id\":\"iphone6s_16G\",\"wxpay_goods_id\":\"1001\",\"goods_name\":\"iPhone6s 16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s 32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]}");
		params.put("out_trade_no", "201704200948461500601217");
		params.put("total_fee", "1");
		params.put("spbill_create_ip", "14.23.150.211");
		params.put("notify_url", "http://125.35.75.142:4322/yesway-pay-center-gateway/weixinnotify/weixinNotify_url");
		params.put("trade_type", "APP");
		params.put("attach", "附加数据");
		
		//
		//生成微信签名，调用微信下单API接口前		
		//
		String asciiString = StringUtil.linkString(params, "&");
		//拼接asciiString和商户密钥
		String key="C1C9F937E430476D99E572ADA6C47779";
		asciiString +="&key="+key;
		//md5编码并转成大写
		String sign=SecurityUtils.getMD5(asciiString).toUpperCase();		
		params.put("sign", sign);
		
		String xmlStr = XMLUtil.map2xmlBody(params, "xml");
		
		try {
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = HttpUtil.postByXml(WEIXIN_UNIFIE_DORDER_URL, xmlStr);
		
		
		
		System.out.println("排序字符串参数：");
		System.out.println(asciiString);
		System.out.println("签名sign：");
		System.out.println(sign);
		System.out.println("Map2XML:");
		System.out.println(xmlStr);
		System.out.println("下单结果：");
		System.out.println(result);
		
		StringBuffer resultXML = new StringBuffer();
		resultXML.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
				.append("<xml>")
				.append("<return_code>SUCCESS</return_code>")
				.append("<return_msg>OK</return_msg>")
				.append("<appid>wxcc87001da05f04a0</appid>")
				.append("<mch_id>1422167202</mch_id>")   
				.append("<nonce_str>MxhGAmBJhPLpgig5</nonce_str>") 
				.append("<sign>7572E3D137C361A5A43A5B7344BAEF05</sign>")
				.append("<result_code>SUCCESS</result_code>")   
				.append("<prepay_id>wx201702241742086d6844371f0793257137</prepay_id>")
				.append("<trade_type>APP</trade_type>")
				.append("</xml>");

		Map<String,String> map = XMLUtil.readStringXmlOut(resultXML.toString());
		System.out.println(map);
		
		
	}
}
