package cn.yesway.pay.center.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

import cn.yesway.pay.center.service.alipay.AlipaySubmitQuery;
import cn.yesway.pay.center.util.ConfigUtil;
import cn.yesway.pay.center.util.StringUtil;

/**
 * @author 退款测试类
 *  2017年3月2日上午11:13:28
 *  RefundTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class RefundTest {
	private final Logger logger = Logger.getLogger(this.getClass());
	private String ALIPAY_APPID = ConfigUtil.getProperty("ALIPAY_APPID");
	private static String INPUT_CHARSET = ConfigUtil.getProperty("INPUT_CHARSET");
	private static final String ALIPAY_GATWAY_URL = "https://openapi.alipaydev.com/gateway.do";
	private static final String APP_ID = "2016072900117404";
	private static final String YESWAY_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCaMirs9SR9qbSr7gN6YyqhYf7nk8+fcsEsThu3NF0b0hAAri1pR6VpYQ/3T98wke1UOfczSwhJWoFWn7vLuklb/IAlozcQpi3Q5YjkTYL9CHoVHYQ+CO17LXlbQarT1tYnbPB/V0MPiSkK3wJoGBMAhd44PaMcwByUs0cb3WS9GCW6WyKvXb8VFoDSoPVhEmflVlI4nr5n8mhF9FgHXyb3WaSbeI3db2LiwKbpFVNGPCPHamTb/6TFyH1Becjq/ughqXSjqymZeMDu1SoHcwUFTH0unMeiRqgIMTlQBpu6uy5ngE+zWpORo7jogPZCjMl2M2mMSAn8Ld2aFMYZpxhRAgMBAAECggEANYda7X3AmDs0tEbhQDhy0Sly0zO17PVqaBYJexUbiY/n7ESLETRFEQsX/tPkeDkAea/RyCWxD73yjn7PM89Mrjk+x1IcyJPX7d/xIMhjw7GQqAniIaL5xjwoF+z4/6diGrev2ZUSe68y50o5zKcm2ZSfa0jCejWa9mgyNFMsKJRSCMG00Pc3FQB9k2fCz/Cu6Y+qFydShfBsI3DTvJx+hC8Eh/Urrl1rw2vdw3rsfYiG/hDurYLIXriCl6PVnWGY6W4pEKyQ/NkFE4zOMe1QQjaSskuS1v+PzvBD7zrTPKeA9gm5f5UNPA2QC8josPeNQ+53cKpxCswJ3NWQmWDgwQKBgQDXSZb3lDGwj/0QqMYBrdrsznmaRWeAEz6DFZUNTlM1iRgn+nwWq7nfY6mbyYv+Uuy8JkKy32opLKv3/Z8o4CihuXIHYZkBjByXEfeAw3uAVKaudWgPyf4HoyWxJaHyu0I/2xzyjikqeNGA/7Z0h+4oF9wgdPMMWahTBgxl1z/r+QKBgQC3WwqFs6PHt+K2QUR2PaXJzqwoYnZQ5OakXcOjMcIauAUCWdSO1EIHxLkearo+5vGZoLpzNJHn6vkPqoPHBIiKigdaAeGHLI/7IxHrZeL9+79C2SMETwkTdGGLqNODGE8cjYFyQybNPabuC69OAPdLpLcvLPuPpxPxQalSx6K1GQKBgBZbiBgSBc8GRQuz20xVV8T79Fj3GDaOyREn7qDBH5cRs7aDJtvb3aO7X+PT3Hv+9KLdflPBK+82um04o4zFKrMQ5SyrEA6/FEEEFqYCpxK+t8iqXLI3EfCNGt74v4bLt6eWA72U0eWdQnSZGcOkUjFLuQqN2qfPr9FfcW4qGnNRAoGAO1cRjrfWt5pBmy6AU6wb7bYfbWqAVCngoy1dVYGTCkaoCFy+OANl41G9f6SW5iij5E5qSUKoPeCD/pf20gAbmIqXWn/lV3QKnQJ6mC5/SiehfKb9eDWEAHHX5/UZjwHdCKabUDl/Y0kir0PE1n/liOukBXaPa6xs/dV8UTge5nECgYAOyhZKxTzQr9+l8Na/YroqxCx759D6Qn6OZRgbNl6xttNIYOl9QI4AAXhSS3PstMpLtgRQkQZqsz5RxffzI5ng5v5N96DZxIVBtFIsMiuePaMZJ+Dy2J6DFr9QZaQBVNZQR/YXas2vyGWsWFlGXnw5C4w7hNT/zfyoQkXI9QEKuw==";
	private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1r4F2QOONBnNvDle/w9RYO+LV6PimU0yiNrRB6k8jnFGQAdeuZNb++pyr4ak6B8KiyJEJbukTcCrAzNJyY0hXfsKxVT0R6E1WwJMksvV+iSK6Br3a/YScX7lOPUdiBw76kXTIQd3rWZNcye9se5In2bj5IXDOd8/I6qbsQ57Rra9b+HDRbV4wacR+ubofqCb8e1mQsK7GuDe0UkTKpHbb52fXLv0b11z5NkLD12bpBH6lSLDDI68trRzQCINxrRlDrnfTvsydBuvMpA4NhUiZXiZ3IlisvUeVB4nNKFlsBXU4y1k0+yjYSbs9XP3A/xqb1ezTMm22Qn2NOVtMnUWUQIDAQAB";
	private static final String CHARSET = "utf-8";
	private static final String SIGN_TYPE = "RSA2";
	//@Test
	public void test() throws Exception{
		Map<String, String> params = new Hashtable<String, String>();
		String sign="kPbQIjX+xQc8F0/A6/AocELIjhhZnGbcBN6G4MM/HmfWL4ZiHM6fWl5NQhzXJusaklZ1LFuMo+lHQUELAYeugH8LYFvxnNajOvZhuxNFbN2LhF0l/KL8ANtj8oyPM4NN7Qft2kWJTDJUpQOzCzNnV9hDxh5AaT9FPqRS6ZKxnzM=";
		// 退款当天日期
		String refund_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		// 退款详细数据
		//String bizContent = outTradeNo+"^"+totalAmount+"^"+outRefundNo;
		params.put("app_id", ALIPAY_APPID);//商户订单号
		/*params.put("method", "alipay.trade.refund");//商户订单号
		params.put("charset", INPUT_CHARSET);//商户订单号
		params.put("sign_type", "RSA2");//商户订单号
		params.put("sign", sign);//商户订单号
		params.put("timestamp", refund_date);//商户订单号
		params.put("version", "1.0");//商户订单号
		params.put("biz_content","22");//商户订单号
*/		params.put("out_trade_no", "11");//商户订单号
		params.put("out_request_no", "120");// 退款单号
		params.put("refund_amount", "100");// 金额
		
		//建立请求
		String sHtmlText = AlipaySubmitQuery.buildRequest("", "", params);		
		logger.info("接收到的数据=" + sHtmlText);
		
	}
	
	@Test
	public void test2() throws AlipayApiException{
		AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATWAY_URL,APP_ID,YESWAY_PRIVATE_KEY,"JSON",CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		//request.setNotifyUrl("http://125.35.75.142:4321/yesway-pay-center-gateway/alipaynotify/alipayNotify_url");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("out_trade_no", "1000201702141332");
		params.put("refund_amount", "0.01");
		params.put("sign_type", "RSA2");
		
//		params.put("goods_detail", "[{\"goods_id\":\"iphone6s_16G\",\"wxpay_goods_id\":\"1001\",\"goods_name\":\"iPhone6s16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]");


		String linkStr = StringUtil.linkString(params, "&");
		System.out.println(linkStr);
		
		params.put("sign", "aiJgKgHbBV2jWixhA86YhUd8I86UkN2Xh0R82FXInmzJTcPIvKOq5lyiKqigIjaLnj20VZywv7z9W0jKzvNM7RYOf+6S4xsAE1X8RVF2nafF9SJpHbWxTVKfaDVNRmHKsffipbqGrDEJwGjxfwYn/ElJ3EJd+dni5EFBKPV8h8qAsuKgk6vokL27D4B3SI/gZWCXevfv3gYH4W1qAMsafDO9KgwH5RtZe/RPFmpaAc0chSk4K8GjWtllG4dNR9avCChJ+hPCAKQBrdGR62sMLaP2tNjcYzEQSj06JnYQBz/1xaLrpm6p/IfYo3Yo1XGyKC/2Z7aeHPfvteH2HpSOXw==");
		String json = JSON.toJSONString(params);		
		System.out.println(json);
		
		
		request.setBizContent(json);
		AlipayTradeRefundResponse response = alipayClient.execute(request);
		System.out.println(JSON.toJSON(response));
		if(response.isSuccess()){
			System.out.println("调用成功");			
		} else {
			System.out.println("调用失败");
		}
	}
	
}
