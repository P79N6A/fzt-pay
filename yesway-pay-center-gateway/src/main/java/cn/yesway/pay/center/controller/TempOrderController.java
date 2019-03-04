package cn.yesway.pay.center.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yesway.pay.center.exception.BaseException;
import cn.yesway.pay.center.exception.InvokingWeiXinAPIException;
import cn.yesway.pay.center.exception.handle.ExceptionHandlerFactory;
import cn.yesway.pay.center.lisener.TempUnifiedOrderThread;
import cn.yesway.pay.center.util.HttpUtil;
import cn.yesway.pay.center.util.SecurityUtils;
import cn.yesway.pay.center.util.StringUtil;
import cn.yesway.pay.center.util.XMLUtil;
import cn.yesway.pay.center.vo.MessageHeader;
import cn.yesway.pay.order.service.GetwayService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *订单支付测试类<br/>
 * 用于临时测试微信下单和支付
 *
 */
@Controller
@RequestMapping("/pay")
public class TempOrderController {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Value("#{globalConfig.WEIXIN_APPID}")
	private String WEIXIN_APPID ;
	@Value("#{globalConfig.WEIXIN_MCHID}")
	private String WEIXIN_MCHID ;
	@Value("#{globalConfig.WEIXIN_NOTIFY_URL}")
	private String WEIXIN_NOTIFY_URL ;
	@Value("#{globalConfig.WEIXIN_MCH_API_SECRET}")
	private String WEIXIN_MCH_API_SECRET ;
	
	@Autowired
	private GetwayService getwayService;
	/**
	 * 微信统一下单接口链接
	 */
	private final String WEIXIN_UNIFIE_DORDER_URL ="https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	@RequestMapping("/weixin/unifiedorder")
	@ResponseBody
	public Map<String, Object> weixinUnifiedOrder(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String,Object>();
		logger.info("开始进入微信‘统一下单’方法 ["+request.getRequestURI()+"]");
		MessageHeader messageheader = (MessageHeader) request.getAttribute("messageheader");
		JSONObject sdata =JSONObject.parseObject((String)request.getAttribute("sdata"));
	    
		logger.info(messageheader);
		logger.info(sdata);
		//
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", WEIXIN_APPID);
		params.put("mch_id", WEIXIN_MCHID);		
		params.put("out_trade_no", (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+RandomUtils.nextInt(10000));
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16)+"");			
		params.put("body", "购买手机");
		params.put("detail", "{\"goods_detail\":[{\"goods_id\":\"iphone6s_16G\",\"wxpay_goods_id\":\"1001\",\"goods_name\":\"iPhone6s 16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s 32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]}");
		params.put("total_fee", "1");
		params.put("spbill_create_ip", "14.23.150.211");
		params.put("notify_url", WEIXIN_NOTIFY_URL);
		params.put("trade_type", "APP");
		params.put("attach","{\"aa\":\"123\"}");	
		
		String httpResult = tempPayUnifiedOrder(params);		
		
		if(StringUtils.isEmpty(httpResult)){
			logger.error("调用微信统一下单接口出现错误");
			returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new InvokingWeiXinAPIException())));
			return returnMap;
		}
		Map<String,String> map = XMLUtil.readStringXmlOut(httpResult);
		String returnCode = map.get("return_code");
		String resultCode = map.get("result_code");
		String prepay_id = null;
		if("FAIL".equals(returnCode)){
			logger.error("调用微信统一下单接口出现错误:"+map);
			returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new InvokingWeiXinAPIException())));
			return returnMap;
		}
		
		//1  验证签名 
		//...
		
		//2 得到微信预支付交易会话标识（prepay_id）
		if("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)){
			prepay_id = map.get("prepay_id");
			MessageHeader resultMsgHeader = new MessageHeader();
			resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
			returnMap.put("messageheader", resultMsgHeader);
		
			Map<String, Object> paramsA = new HashMap<String, Object>();
			paramsA.put("prepayid", prepay_id);
			paramsA.put("appid", WEIXIN_APPID);
			paramsA.put("partnerid", WEIXIN_MCHID);
			paramsA.put("package", "Sign=WXPay");
			paramsA.put("noncestr", RandomStringUtils.randomAlphanumeric(16)+"");
			paramsA.put("timestamp", System.currentTimeMillis()+"");
			
			String asciiString = StringUtil.linkString(paramsA, "&");
			//拼接asciiString和商户密钥
			String key = WEIXIN_MCH_API_SECRET;
			asciiString +="&key="+key;
			//md5编码并转成大写
			String sign=SecurityUtils.getMD5(asciiString).toUpperCase();		
			paramsA.put("sign", sign);
			
			String rData = JSONObject.toJSONString(paramsA);
			returnMap.put("rdata", rData);
			
			//保存订单到数据库
			//...
			try {
				params.put("subject","购买手机");
				params.put("pay_tool_type","2");
				params.put("total_amount","0.04");
				
				sdata = (JSONObject)JSONObject.toJSON(params);	
				
				
				Thread thread = new TempUnifiedOrderThread(messageheader, sdata, prepay_id, getwayService);
				thread.start();				
			} catch (Exception e) {
				logger.error("保存订单出错");
				returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new Exception())));
				return returnMap;
			}
		}			
				
		logger.info("结束微信‘统一下单’方法 ["+request.getRequestURI()+"]");
		return returnMap;
	}
	/**
	 * 调用微信接口进行下单
	 * @param sdata
	 * @return String
	 */
	private String tempPayUnifiedOrder(Map<String, Object> params) {	
		logger.info("调用微信接口进行下单 start");		
		//
		//生成微信签名，调用微信下单API接口前		
		//
		String asciiString = StringUtil.linkString(params, "&");
		//拼接asciiString和商户密钥
		String key= WEIXIN_MCH_API_SECRET;
		asciiString +="&key="+key;
		//md5编码并转成大写
		String sign=SecurityUtils.getMD5(asciiString).toUpperCase();		
		params.put("sign", sign);
		
		String xmlStr = XMLUtil.map2xmlBody(params, "xml");
		String httpResult = HttpUtil.postByXml(WEIXIN_UNIFIE_DORDER_URL, xmlStr);
		
		System.out.println();
		System.out.println("排序字符串参数：");
		System.out.println(asciiString);
		System.out.println("Map2XML:");
		System.out.println(xmlStr);
		System.out.println("下单结果：");
		System.out.println(httpResult);
		logger.info("调用微信接口进行下单 end");
		return httpResult;
	}
	
}
