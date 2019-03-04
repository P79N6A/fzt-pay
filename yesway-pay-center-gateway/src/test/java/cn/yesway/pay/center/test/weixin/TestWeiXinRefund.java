
package cn.yesway.pay.center.test.weixin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.dom4j.DocumentException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.yesway.pay.center.exception.InvokingWeiXinAPIException;
import cn.yesway.pay.center.exception.handle.ExceptionHandlerFactory;
import cn.yesway.pay.center.util.HttpUtil;
import cn.yesway.pay.center.util.SecurityUtils;
import cn.yesway.pay.center.util.StringUtil;
import cn.yesway.pay.center.util.XMLUtil;

/**
 * @author 微信退款接口测试
 *  2017年3月7日下午7:55:06
 *  TestWeiXinRefund
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class TestWeiXinRefund {

private final static String WEIXIN_UNIFIE_DORDER_URL ="https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	public static void main(String[] args) throws DocumentException {
		unifiedOrder();

	}
	public static void unifiedOrder() throws DocumentException{		
		/*<xml>
		   <appid>wx2421b1c4370ec43b</appid>
		   <mch_id>10000100</mch_id>
		   <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
		   <op_user_id>10000100</op_user_id>
		   <out_refund_no>1415701182</out_refund_no>
		   <out_trade_no>1415757673</out_trade_no>
		   <refund_fee>1</refund_fee>
		   <total_fee>1</total_fee>
		   <transaction_id></transaction_id>
		   <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
		</xml> */
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", "wxcc87001da05f04a0");
		params.put("mch_id", "1422167202");		
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));		
//		params.put("op_user_id", "1422167202@1422167202945488");
		params.put("out_refund_no", "201703161451551617");
		params.put("out_trade_no", "10001000010020170601173022000656");
		params.put("refund_fee", "1");
		params.put("total_fee", "1");
		params.put("transaction_id", "");
		
		//
		//生成微信签名，调用微信下单API接口前		
		//
		Long refundAmount=(long) 25.5;
		String totalAmount="25";
		String payAmountLi = "";
		//double totalAmount = 0;
		payAmountLi=BigDecimal.valueOf(refundAmount).multiply(new BigDecimal(100)).toString();    
		//payAmountLi=(double)(Math.round(Float.parseFloat(refundAmount))*100.0);//这样为转换为分
		//totalAmount=(Math.round(Float.parseFloat(totalAmount))*100.0);//这样为转换为分
		System.out.println(""+payAmountLi);
		String asciiString = StringUtil.linkString(params, "&");
		//拼接asciiString和商户密钥
		String key="C1C9F937E430476D99E572ADA6C47779";
		asciiString +="&key="+key;
		//md5编码并转成大写
		String sign=SecurityUtils.getMD5(asciiString).toUpperCase();		
		params.put("sign", sign);
		
		String xmlStr = XMLUtil.map2xmlBody(params, "xml");
		String wxUrl = WEIXIN_UNIFIE_DORDER_URL;  
		String weixinPost = null;
		try {
			weixinPost = ClientCustomSSL.doRefund(wxUrl, xmlStr).toString();
			System.out.println("调用退款接口返回信息:"+weixinPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//String result = HttpUtil.postByXml(WEIXIN_UNIFIE_DORDER_URL, xmlStr);
		Map<String, String> mapParam = XMLUtil.readStringXmlOut(weixinPost);
		String result_code = mapParam.get("result_code");// SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
		String return_msg = mapParam.get("return_msg");// SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
		if ("FAIL".equals(result_code)) {
			
		}
		System.out.println("排序字符串参数：");
		System.out.println(asciiString);
		System.out.println("签名sign：");
		System.out.println(sign);
		System.out.println("Map2XML:");
		/*System.out.println(xmlStr);
		System.out.println("下单结果：");
		System.out.println(result);*/
		
	
		
		
	}
}
