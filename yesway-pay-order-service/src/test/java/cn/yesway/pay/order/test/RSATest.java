
package cn.yesway.pay.order.test;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.rsa.PrivateRSA;
import cn.yesway.pay.order.rsa.PublicRSA;
import cn.yesway.pay.order.service.OrderService;

/**
 * @author ghk
 *  2017年2月13日上午10:14:02
 *  RSATest测试方法加密解密的方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class RSATest {

	Logger logger = Logger.getLogger(RSATest.class);
	
	/*@Autowired
	OrderService orderService;*/
	
	/*@Test
	public void test2(){
		Order order = new Order();
		order.setOrderId("001");
		order.setSubject("手机");
		order.setTotal_amount(100);
		orderService.unifiedorder(order);
	}*/
	@Test
	public void test(){
		
		String data="";
		String userKey="123456";
		//回调地址
		String returnUrl="";
		String data2="";
		//访问的支付中心的统一下单地址
		String pay_url_gateway="http://localhost:8080/yesway-pay-center-gateway/pay/unifiedorder";
		try {
			Map messageheader = new HashMap();
			//messageheader.put("token", "token123");
			Orders order = new Orders();
			//封装数据
			String orderreqJson = orderReqInfo(order,messageheader);
			logger.info("\n 请求的json数据为:"+orderreqJson.toString());
			//对请求的数据进行rsa加密操作
			data=PrivateRSA.getInstance().encryptByPrivateKey("11111");
			data2=PublicRSA.getInstance().decryptByPublicKey(data);
			logger.info("\n=========== 公钥加密的data数据是:" + data);
			String reqJson = this.packageReqInfo(data, userKey,returnUrl);
			String castJson = HttpUtils.doPost(pay_url_gateway, reqJson, null, "UTF-8");
			logger.debug("\n  回调返回的结果信息:"+castJson.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 封装请求信息
	 * @param req
	 * @param 
	 * @author ghk
	 * @Date 
	 */
	private String packageReqInfo(Object req, String userKey ,String reTurnUrl) {
		Map<String,Object> reqgmonlineMap = new HashMap<String, Object>();
		Map header = new HashMap();
		header.put("token","token123");
		reqgmonlineMap.put("messageheader", header);
		//SimpleDateFormat sf = new SimpleDateFormat(DateUtil.DATETIME_FORMAT);
		//String date = sf.format(new Date());
		reqgmonlineMap.put("userKey", userKey);
		reqgmonlineMap.put("data", req);
		reqgmonlineMap.put("ReturnUrl",reTurnUrl);
		String reqJson = JSON.toJSONString(reqgmonlineMap);
		return reqJson;
	}
   public String orderReqInfo(Orders order,Map<String,String> header){
		
		Map messageheader = new HashMap();
		Map data = new HashMap();
		//messageheader.put("messageheader", header);
		data.put("orderId","001");
		data.put("total_amount", 100);
		data.put("subject", "手机");
		
		messageheader.put("order", data);
		//String reqJson=JSON.toJSONString(messageheader);
		//JSONObject j = JSONObject.fromObject(messageheader); // j = new JSONObject();
		String reqJson=JSON.toJSONString(messageheader);
	    //return j.toString();
		return reqJson;
		
	}
}
