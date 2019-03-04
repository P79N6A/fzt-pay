
package cn.yesway.pay.center.test.alipay;

import java.util.HashMap;
import java.util.Map;

import cn.yesway.pay.center.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * @author alipay退款查询
 *  2017年3月3日下午1:31:53
 *  TestAlipayRefundQueryOrder
 */

public class TestAlipayRefundQueryOrder {

	public static void main(String[] args) throws AlipayApiException {
	AlipayClient alipayClient = new DefaultAlipayClient(Contants.ALIPAY_GATWAY_URL,Contants.APP_ID,Contants.YESWAY_PRIVATE_KEY,"JSON",Contants.CHARSET,Contants.ALIPAY_PUBLIC_KEY,Contants.SIGN_TYPE);
	AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
	//request.setNotifyUrl("http://125.35.75.142:4322/yesway-pay-center-gateway/alipaynotify/alipayNotify_url");

	Map<String,Object> params = new HashMap<String,Object>();
	params.put("out_trade_no", "201703021643234");
	params.put("out_request_no", "201703021643234");
	//params.put("refund_amount", "0.01");
	//params.put("refund_reason", "不喜欢");
	params.put("sign_type", Contants.SIGN_TYPE);
	
	String linkStr = StringUtil.linkString(params, "&");
	System.out.println(linkStr);
	
	String mchPrivateKey="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCaMirs9SR9qbSr7gN6YyqhYf7nk8+fcsEsThu3NF0b0hAAri1pR6VpYQ/3T98wke1UOfczSwhJWoFWn7vLuklb/IAlozcQpi3Q5YjkTYL9CHoVHYQ+CO17LXlbQarT1tYnbPB/V0MPiSkK3wJoGBMAhd44PaMcwByUs0cb3WS9GCW6WyKvXb8VFoDSoPVhEmflVlI4nr5n8mhF9FgHXyb3WaSbeI3db2LiwKbpFVNGPCPHamTb/6TFyH1Becjq/ughqXSjqymZeMDu1SoHcwUFTH0unMeiRqgIMTlQBpu6uy5ngE+zWpORo7jogPZCjMl2M2mMSAn8Ld2aFMYZpxhRAgMBAAECggEANYda7X3AmDs0tEbhQDhy0Sly0zO17PVqaBYJexUbiY/n7ESLETRFEQsX/tPkeDkAea/RyCWxD73yjn7PM89Mrjk+x1IcyJPX7d/xIMhjw7GQqAniIaL5xjwoF+z4/6diGrev2ZUSe68y50o5zKcm2ZSfa0jCejWa9mgyNFMsKJRSCMG00Pc3FQB9k2fCz/Cu6Y+qFydShfBsI3DTvJx+hC8Eh/Urrl1rw2vdw3rsfYiG/hDurYLIXriCl6PVnWGY6W4pEKyQ/NkFE4zOMe1QQjaSskuS1v+PzvBD7zrTPKeA9gm5f5UNPA2QC8josPeNQ+53cKpxCswJ3NWQmWDgwQKBgQDXSZb3lDGwj/0QqMYBrdrsznmaRWeAEz6DFZUNTlM1iRgn+nwWq7nfY6mbyYv+Uuy8JkKy32opLKv3/Z8o4CihuXIHYZkBjByXEfeAw3uAVKaudWgPyf4HoyWxJaHyu0I/2xzyjikqeNGA/7Z0h+4oF9wgdPMMWahTBgxl1z/r+QKBgQC3WwqFs6PHt+K2QUR2PaXJzqwoYnZQ5OakXcOjMcIauAUCWdSO1EIHxLkearo+5vGZoLpzNJHn6vkPqoPHBIiKigdaAeGHLI/7IxHrZeL9+79C2SMETwkTdGGLqNODGE8cjYFyQybNPabuC69OAPdLpLcvLPuPpxPxQalSx6K1GQKBgBZbiBgSBc8GRQuz20xVV8T79Fj3GDaOyREn7qDBH5cRs7aDJtvb3aO7X+PT3Hv+9KLdflPBK+82um04o4zFKrMQ5SyrEA6/FEEEFqYCpxK+t8iqXLI3EfCNGt74v4bLt6eWA72U0eWdQnSZGcOkUjFLuQqN2qfPr9FfcW4qGnNRAoGAO1cRjrfWt5pBmy6AU6wb7bYfbWqAVCngoy1dVYGTCkaoCFy+OANl41G9f6SW5iij5E5qSUKoPeCD/pf20gAbmIqXWn/lV3QKnQJ6mC5/SiehfKb9eDWEAHHX5/UZjwHdCKabUDl/Y0kir0PE1n/liOukBXaPa6xs/dV8UTge5nECgYAOyhZKxTzQr9+l8Na/YroqxCx759D6Qn6OZRgbNl6xttNIYOl9QI4AAXhSS3PstMpLtgRQkQZqsz5RxffzI5ng5v5N96DZxIVBtFIsMiuePaMZJ+Dy2J6DFr9QZaQBVNZQR/YXas2vyGWsWFlGXnw5C4w7hNT/zfyoQkXI9QEKuw==";
	String sign = AlipaySignature.rsa256Sign(linkStr, mchPrivateKey, Contants.CHARSET);		
	params.put("sign", sign);
	
	String json = JSON.toJSONString(params);		
	System.out.println(json);
	
	request.setBizContent(json);		 
	AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
    String data =response.getBody();
    System.out.println("Alipay退款查询接口返回信息"+data.toString());
	if(response.isSuccess()){
		System.out.println("调用成功");			
	} else {
		System.out.println("调用失败");
	}
	
	JSONObject object=JSONObject.parseObject(data);
	JSONObject ob = (JSONObject) object.get("alipay_trade_fastpay_refund_query_response");
	
	Map data1 = new HashMap();
	Map<String,Object> messageheader = new HashMap<String, Object>();
	Map<String,Object> param = new HashMap<String, Object>();
	String code =  (String)ob.get("code");
	String msg =  (String)ob.get("msg");
	messageheader.put("code", code);
	messageheader.put("msg", msg);
	String out_trade_no =  (String)ob.get("out_trade_no");
	String out_request_no =  (String)ob.get("out_request_no");
	String refund_amount =  (String)ob.get("refund_amount");
	String total_amount =  (String)ob.get("total_amount");
	String trade_no =  (String)ob.get("trade_no");
	param.put("out_trade_no", out_trade_no);
	param.put("out_request_no", out_request_no);
	param.put("refund_amount", refund_amount);
	param.put("total_amount", total_amount);
	param.put("trade_no", trade_no);
	//JSONObject j = JSONObject.parseObject(text)
	data1.put("messageheader", messageheader);
	data1.put("rdata", param);
	System.out.println("数据:"+data1.toString());
  }
}
