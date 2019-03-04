package cn.yesway.pay.center.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.fastjson.JSONObject;

public class TestJSONObject {

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", "12");
		params.put("mch_id", "34");		
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));		
		params.put("body", "购买手机");
		params.put("detail", "{\"goods_detail\":[{\"goods_id\":\"iphone6s_16G\",\"wxpay_goods_id\":\"1001\",\"goods_name\":\"iPhone6s 16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s 32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]}");
		params.put("out_trade_no", (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+RandomStringUtils.randomAlphanumeric(100000));
		params.put("total_fee", RandomUtils.nextInt(100000));
		params.put("spbill_create_ip", "14.23.150.211");
		params.put("notify_url", "");
		params.put("trade_type", "APP");
		params.put("attach","{\"aa\":\"123\"}");
		JSONObject o = (JSONObject)JSONObject.toJSON(params);
		System.out.println(o);
	}

}
