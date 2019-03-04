package cn.yesway.pay.center.test.weixin;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.yesway.pay.center.exception.BaseException;
import cn.yesway.pay.center.exception.InvokingWeiXinAPIException;
import cn.yesway.pay.center.exception.handle.ExceptionHandlerFactory;
import cn.yesway.pay.center.util.HttpUtil;
import cn.yesway.pay.center.util.SecurityUtils;
import cn.yesway.pay.center.util.StringUtil;
import cn.yesway.pay.center.util.XMLUtil;
import cn.yesway.pay.center.vo.MessageHeader;
import cn.yesway.pay.order.entity.Refund;

/**
 * @author 退款查询 2017年3月8日上午10:36:48 TestWeiXinRefundQuery
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class TestWeiXinRefundQuery {

	private final static String WEIXIN_REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";

	public static void main(String[] args) throws DocumentException {
		unifiedOrder();

	}

	public static void unifiedOrder() throws DocumentException {
		/*
		 * <xml> <appid>wx2421b1c4370ec43b</appid> 
		 * <mch_id>10000100</mch_id>
		 * <nonce_str>0b9f35f484df17a732e537c37708d1d0</nonce_str>
		 * <out_refund_no></out_refund_no>
		 * <out_trade_no>1415757673</out_trade_no> 
		 * <refund_id></refund_id>
		 * <transaction_id></transaction_id>
		 * <sign>66FFB727015F450D167EF38CCC549521</sign> </xml>
		 */

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", "wxcc87001da05f04a0");
		params.put("mch_id", "1422167202");
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		params.put("out_refund_no", "");
		params.put("out_trade_no", "201703091729469066");
		params.put("transaction_id", "");

		//
		// 生成微信签名，调用微信下单API接口前
		//
		String asciiString = StringUtil.linkString(params, "&");
		// 拼接asciiString和商户密钥
		String key = "C1C9F937E430476D99E572ADA6C47779";
		asciiString += "&key=" + key;
		// md5编码并转成大写
		String sign = SecurityUtils.getMD5(asciiString).toUpperCase();
		params.put("sign", sign);

		String xmlStr = XMLUtil.map2xmlBody(params, "xml");

		String result = HttpUtil.postByXml(WEIXIN_REFUND_QUERY_URL, xmlStr);
		System.out.println("调用微信退款查询接口回调返回信息："+result.toString());
		if (StringUtils.isBlank(result)) {
			System.out.println("调用接口失败");
		}
		Map<String, String> mapParam = XMLUtil.readStringXmlOut(result);
		String result_code = mapParam.get("result_code");
		if ("FAIL".equals(result_code)) {
			System.out.println("调用接口返回信息失败");
		}
		String refundFee=mapParam.get("refund_fee");
		Refund refund = new Refund();
		refund.setRefundid("20170308100810233");
		refund.setOutTradeNo("20170308100810233");
		refund.setRefundamount(new BigDecimal(refundFee));
		refund.setAddtime(new Timestamp(System.currentTimeMillis()));
		refund.setEndtime(new Timestamp(System.currentTimeMillis()));
		//refund.setRefundreason(refundReason);
		
	}

}
