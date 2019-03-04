package cn.yesway.pay.center.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yesway.pay.center.exception.BaseException;
import cn.yesway.pay.center.exception.InvalidParamException;
import cn.yesway.pay.center.exception.InvokingAlipayAPIException;
import cn.yesway.pay.center.exception.InvokingWeiXinAPIException;
import cn.yesway.pay.center.exception.RSAPrivateKeyException;
import cn.yesway.pay.center.exception.handle.ExceptionHandlerFactory;
import cn.yesway.pay.center.lisener.CloseOrderThread;
import cn.yesway.pay.center.lisener.UnifiedOrderThread;
import cn.yesway.pay.center.util.Contants;
import cn.yesway.pay.center.util.FractionDigits;
import cn.yesway.pay.center.util.HttpUtil;
import cn.yesway.pay.center.util.SecurityUtil_AES;
import cn.yesway.pay.center.util.SecurityUtils;
import cn.yesway.pay.center.util.StringUtil;
import cn.yesway.pay.center.util.XMLUtil;
import cn.yesway.pay.center.vo.MessageHeader;
import cn.yesway.pay.order.entity.Credential;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.entity.enums.SignTypeEnum;
import cn.yesway.pay.order.service.CredentialService;
import cn.yesway.pay.order.service.GetwayService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.api.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.api.trade.service.AlipayTradeService;
import com.alipay.api.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.api.trade.service.impl.AlipayTradeServiceImpl.ClientBuilder;

/**
 *  订单管理
 *  2017年2月6日下午2:15:55
 *  OrderController
 */
@Controller
@RequestMapping("/pay")
public class OrderController extends BaseController{
	private final Logger logger = Logger.getLogger(this.getClass());
	@Value("#{globalConfig.WEIXIN_APPID}")
	private String WEIXIN_APPID ;
	@Value("#{globalConfig.WEIXIN_MCHID}")
	private String WEIXIN_MCHID ;
	@Value("#{globalConfig.WEIXIN_NOTIFY_URL}")
	private String WEIXIN_NOTIFY_URL ;
	@Value("#{globalConfig.WEIXIN_MCH_API_SECRET}")
	private String WEIXIN_MCH_API_SECRET ;
	
	@Value("#{globalConfig.ALIPAY_APPID}")
	private String ALIPAY_APPID;
	
	@Value("#{globalConfig.ALIPAY_NOTIFY_URL}")
	private String ALIPAY_NOTIFY_URL;
	
	@Value("#{globalConfig.ALIPAY_GATEWAY}")
  	private static String openApiDomain;   // 支付宝openapi域名
	
	
	/**
     * 出自九五支付中心或者OEM的秘钥（证书）
     */
    private final String FROM = "YESWAY";
    /**
     * 出自支付宝支付平台的证书
     */
    private final String TO = "ALIPAY";
	/**
	 * 微信统一下单接口链接
	 */
	private final String WEIXIN_UNIFIE_DORDER_URL ="https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 微信‘关闭订单’接口链接
	 */
	private final String WEIXIN_CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	/**
	 * 支付宝网关接口链接
	 */
	private final String ALIPAY_GATWAY_URL = "https://openapi.alipaydev.com/gateway.do";
	
	@Autowired
	private GetwayService getwayService;
	
	@Autowired
    private CredentialService credentialService;
	
	/*@Autowired
	private PaymentRecordsService paymentRecordsService;*/
	
	
	/**
	 * 统一下单
	 * @throws IOException 
	 */
	@RequestMapping("/unifiedorder")
	@ResponseBody
	public Map<String, Object> unifiedOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> returnMap = new HashMap<String,Object>();
		logger.info("开始进入‘统一下单’方法 ["+request.getRequestURI()+"]");
		MessageHeader messageheader = (MessageHeader) request.getAttribute("messageheader");
		JSONObject sdata =JSONObject.parseObject((String)request.getAttribute("sdata"));
		JSONObject payOrderData =JSONObject.parseObject((String)request.getAttribute("payOrderNo"));
	    
		logger.info(messageheader);
		logger.info(sdata);
		logger.info(payOrderData);
	    
		
	    //终端类型
		String  terminalType = messageheader.getTerminal_type();
	    //判断订单总金额是否符合金钱格式
		//
		String payOrderNo = payOrderData.getString("payOrderNo");
		
		String totalAmountStr = sdata.getString("total_amount");
		
		try {
			Double.parseDouble(totalAmountStr);
		} catch (Exception e) {
			logger.error("解析订单金额出错");
			returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new InvalidParamException("订单金额参数异常")));
			return returnMap;
		}
		//判断'out_trade_no'是否已经存在，是否是重复提交的订单
		/*String outTradeNo = sdata.getString("out_trade_no");
		int c = getwayService.queryOrdersCount(outTradeNo);
		if(c>0){
			logger.error("订单号已经存在："+outTradeNo);
			returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new RepeatOrderException())));
			return returnMap;
		}*/
		//从数据库证书表读取AES算法密钥
		Credential aesCredential = credentialService.getCredential(FROM,messageheader.getOemid(), SignTypeEnum.AES.name());
		if(aesCredential==null){
        	logger.error(">>>统一下单读取AES算法密钥出错",new Exception());
        	returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new Exception()));
			return returnMap;
        }else if(StringUtils.isEmpty(aesCredential.getCredential())){
        	logger.error(">>>统一下单读取AES算法密钥出错",new Exception());
        	returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new Exception()));
			return returnMap;
        }
		/*
		 * 使用微信支付处理下单请求
		 * 判断pay_tool_type如果是微信的话，调用微信接口在微信支付服务后台生成预支付交易单，将正确结果返回给九五支付SDK，供APP调起支付。
		 * 第三方支付工具类型:1支付宝、2微信支付
		 */
		int payToolType = sdata.getInteger("pay_tool_type");
		if(payToolType == 2){
			//验证订单的失效时间
			//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
			//注意：最短失效时间间隔必须大于5分钟
			//
			returnMap = validatetOrderTimeExpire(sdata);
			if(returnMap.size() > 0){
				return returnMap;
			}
			
			//调用微信接口进行下单
			String httpResult = payUnifiedOrder(sdata,payOrderNo);	//String payOrderNo	
			
			if(StringUtils.isEmpty(httpResult)){
				logger.error("调用微信统一下单接口出现错误");
				returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new InvokingWeiXinAPIException()));
				return returnMap;
			}
			Map<String,String> map = XMLUtil.readStringXmlOut(httpResult);
			String returnCode = map.get("return_code");
			String resultCode = map.get("result_code");
			String prepay_id = null;
			if("FAIL".equals(returnCode)){
				logger.error("调用微信统一下单接口出现错误:"+map);
				returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new InvokingWeiXinAPIException()));
				return returnMap;
			}
			
			//1  验证签名 
			//...
			
			//2 得到微信预支付交易会话标识（prepay_id）
			if("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)){
				//add by 2017-11-21 新增微信扫码处理 start
				String trade_type = map.get("trade_type");
				if("NATIVE".equals(trade_type)){
					map.put("out_trade_no", sdata.getString("out_trade_no"));
					returnMap.put("rdata", map);
					return returnMap;
				}
				//add by 2017-11-21 新增微信扫码处理 end
				prepay_id = map.get("prepay_id");
				MessageHeader resultMsgHeader = new MessageHeader();
				resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
				returnMap.put("messageheader", resultMsgHeader);
				
				/**
				 * 生成微信‘调起支付’接口所需的字符串
				 */
				String rData = generateWeixinTradePayStr(aesCredential, map,terminalType);
				returnMap.put("rdata", rData);
				
				//保存订单到数据库
				//...
				try {
					Thread thread = new UnifiedOrderThread(messageheader, sdata,payOrderNo, prepay_id, getwayService);
					thread.start();				
				} catch (Exception e) {
					logger.error("保存订单出错");
					returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new Exception()));
					return returnMap;
				}
			}			
		}
		/*
		 * 使用支付宝工具进行支付，处理下单请求
		 * 第三方支付工具类型:1支付宝、2微信支付
		 * */
		else if(payToolType == 1){
			//验证订单的失效时间
			//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
			//注意：最短失效时间间隔必须大于5分钟
			//
			returnMap = validatetOrderTimeExpire(sdata);
			if(returnMap.size() > 0){
				return returnMap;
			}
			
			//add by 20171125 支付宝扫码逻辑处理  start
			String trade_type = sdata.getString("trade_type");
			if(StringUtils.isNotEmpty(trade_type)){
				if("NATIVE".equals(trade_type)){
					//预下单
					AlipayF2FPrecreateResult result = alipay_trade_precreate(sdata, payOrderNo);
			        switch (result.getTradeStatus()) {
		            case SUCCESS:
		            	logger.info("支付宝预下单成功: )");
		            	//遍历组织数据，让微信和支付宝扫码支付返回统一的格式。
		            	JSONObject resJson=reassemblyData(result.getResponse());
		            	
		            	returnMap.put("rdata", resJson);
		            	dumpResponse(result.getResponse());
		            	return returnMap;
		            case FAILED:
						
		            	logger.error("支付宝预下单失败!!!:"+result.getResponse());
		            	returnMap.put("messageheader","支付宝预下单失败!!!");
		            	return returnMap;
		            case UNKNOWN:
		            	logger.error("系统异常，预下单状态未知!!!:"+result.getResponse());
		            	returnMap.put("messageheader","系统异常，预下单状态未知!!!");
		                return returnMap;

		            default:
		            	logger.error("不支持的交易状态，交易返回异常!!!:"+result.getResponse());
		            	returnMap.put("messageheader","不支持的交易状态，交易返回异常!!!");
		                return returnMap;
		        }
				}
			}
			//add by 20171125 支付宝扫码逻辑处理  end
			//
			//1.保存订单到数据库
			//
			try {
				Thread thread = new UnifiedOrderThread(messageheader, sdata,payOrderNo, null, getwayService);
				thread.start();				
			} catch (Exception e) {
				logger.error("保存订单出错");
				returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new Exception()));
				return returnMap;
			}
		    
		    Credential rsaCredential = credentialService.getCredential(FROM, TO, SignTypeEnum.RSA_PRIVATE.name());
			if(rsaCredential==null){
	        	logger.error(">>>统一下单读取支付宝RSA_PUBLIC算法密钥出错",new Exception());
	        	returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new Exception()));
				return returnMap;
	        }else if(StringUtils.isEmpty(rsaCredential.getCredential())){
	        	logger.error(">>>统一下单读取支付宝RSA_PUBLIC算法密钥出错",new Exception());
	        	returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new Exception()));
				return returnMap;
	        }
			
		    //将订单转换成支付宝支付 商户支付请求参数,详情请见支付宝支付文档：App支付请求参数说明
		    //
		    String rData=null;
			try {
				rData = generateAlipayTradePayStr(sdata,rsaCredential,aesCredential,terminalType,payOrderNo);
			} catch (AlipayApiException e) {				
				e.printStackTrace();
				logger.error("生成支付宝App支付请求参数出错");
				returnMap.put("messageheader", ExceptionHandlerFactory.getHandler().process(new InvokingAlipayAPIException()));
				return returnMap;
			}
		    returnMap.put("rdata", rData);
		    
		    MessageHeader resultMsgHeader = new MessageHeader();
			resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
			returnMap.put("messageheader", resultMsgHeader);
		}
	    
	    logger.info("结束‘统一下单’方法 ["+request.getRequestURI()+"]");
		return returnMap;		 
	 }
	 /**
	  * @see 支付宝扫码支付
	  * @param sdata
	  * @param payOrderNo
	  * @return
	  */
    public AlipayF2FPrecreateResult alipay_trade_precreate(JSONObject sdata,String payOrderNo) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = sdata.getString("out_trade_no");

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = sdata.getString("subject");

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = sdata.getString("total_amount");

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";
        
        String undiscountableAmount = "0";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = sdata.getString("body");

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = sdata.getString("vin");
        
        // 支付超时，定义为120分钟
        String timeoutExpress = sdata.getString("timeoutExpress");
       
        //回调地址
        String notifyUrl=sdata.getString("notifyUrl");
        //阿里公钥
        String alipublickey=sdata.getString("platform_publickey");
        //appid
        String appid=sdata.getString("appid");
        String privateKey = sdata.getString("private_key");
        //下单地址
        String aliPayGetWayUrl= sdata.getString("pay_get_way_url");
        // 传入订单信息
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
            .setSubject(subject).setTotalAmount(totalAmount)
            .setOutTradeNo(outTradeNo).setUndiscountableAmount(undiscountableAmount)
            .setSellerId(sellerId).setBody(body)
            .setStoreId(storeId).setTimeoutExpress(timeoutExpress)
            .setNotifyUrl(notifyUrl);//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
        ClientBuilder bulider = new AlipayTradeServiceImpl.ClientBuilder();
        // 传入支付参数
        bulider.setAlipayPublicKey(alipublickey).setPrivateKey(privateKey).setAppid(appid).setCharset("GBK").setGatewayUrl(aliPayGetWayUrl).setFormat("json").setSignType("RSA2");
        AlipayTradeService  tradeService = new AlipayTradeServiceImpl(bulider);
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        return result;
    }
    private JSONObject reassemblyData(AlipayResponse response){
    	JSONObject jsonObjectRes=new JSONObject();
    	if (response != null) {
    		String body = response.getBody();
    		@SuppressWarnings("static-access")
			JSONObject jsonBody=new JSONObject().parseObject(body);
    		jsonObjectRes.put("sign", jsonBody.getString("sign"));
    		JSONObject jsnResponse=jsonBody.getJSONObject("alipay_trade_precreate_response");
    		if(jsnResponse==null){
    			return jsonObjectRes;
    		}
    		jsonObjectRes.put("result_code", jsnResponse.getString("msg"));
    		String msg=jsnResponse.getString("msg").toUpperCase();
    		if("SUCCESS".equals(msg)){
    			jsonObjectRes.put("result_msg","OK");
    		}
	    	jsonObjectRes.put("result_code", msg);
	    	jsonObjectRes.put("code_url", jsnResponse.getString("qr_code"));
	    	jsonObjectRes.put("out_trade_no", jsnResponse.getString("out_trade_no"));
	    	jsonObjectRes.put("trade_type","NATIVE");
	    	
    	}
    	return jsonObjectRes;
    }
    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
            	logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                    response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

	/**
	 * 验证订单的失效时间<br/>
	 * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则<br/>
	 * 注意：最短失效时间间隔必须大于5分钟<br/>		
	 * @param sdata
	 * @return
	 */
	private Map<String, Object> validatetOrderTimeExpire(JSONObject sdata) {
		Map<String, Object> returnMap = new HashMap<String,Object>();
		String time_start = sdata.getString("time_start");
		String time_expire = sdata.getString("time_expire");
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		Date timeExpire = null,
				timeStart = null;
		long sysDate = System.currentTimeMillis();
		try {
			timeExpire = format.parse(time_expire);
			timeStart = format.parse(time_start);
		} catch (ParseException e) {			
			e.printStackTrace();
			logger.error("订单失效时间解析错误");
			returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new InvalidParamException("订单失效时间参数错误")));
			return returnMap;
		}
		if(timeExpire.getTime()<sysDate){
			returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new InvalidParamException("订单失效时间参数有误")));
			return returnMap;
		}
		if(timeExpire!=null && timeStart != null){
			long c = timeExpire.getTime() - timeStart.getTime();
			if((timeExpire.getTime() <= timeStart.getTime()) || (c < 5L)){
				returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new InvalidParamException("订单失效时间参数不能小于5分钟")));
				return returnMap;
			}
		}
		
		return returnMap;
	}


	/**
	 * 生成微信‘调起支付’接口请求参数的字符串
	 * @param aesCredential
	 * @param map
	 * @return rData 进过AES加密后的字符串
	 */
	private String generateWeixinTradePayStr(Credential aesCredential,Map<String, String> map,String terminalType) {
		//对返回结果进行加签
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", map.get("appid"));
		params.put("partnerid", map.get("mch_id"));
		params.put("prepayid", map.get("prepay_id"));
		params.put("package", "Sign=WXPay");
		params.put("noncestr", RandomStringUtils.randomAlphanumeric(16));
		params.put("timestamp", String.valueOf((new Date()).getTime()).toString().substring(0, 10));//时间戳位数的问题，安卓13位和10位都可以，ios必须为10位
		//
		//生成微信签名，给终端SDK返回数据前。		
		//
		String asciiString = StringUtil.linkString(params, "&");
		//拼接asciiString和商户密钥
		String key= WEIXIN_MCH_API_SECRET;
		asciiString +="&key="+key;
		//md5编码并转成大写
		String sign=SecurityUtils.getMD5(asciiString).toUpperCase();		
		params.put("sign", sign);
		
		String rData = JSONObject.toJSONString(params);		
		try {
			rData=SecurityUtil_AES.encrypt(rData, aesCredential.getCredential());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return rData;
	}
	
	
	
	/**
	 * 生成支付宝支付请求参数字符串<br/>
	 * 参考文档：https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.aKWJud&treeId=204&articleId=105465&docType=1
	 * @param order
	 * @param rsaCredential
	 * @param aesCredential
	 * @return rData 进过AES加密后的字符串
	 * @throws AlipayApiException
	 * @throws UnsupportedEncodingException
	 */
	private String generateAlipayTradePayStr(JSONObject sdata,Credential rsaCredential,Credential aesCredential,String terminalType,String payOrderNo) throws AlipayApiException, UnsupportedEncodingException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("app_id", ALIPAY_APPID);
		params.put("method", "alipay.trade.app.pay");
		params.put("format", "json");
		params.put("charset", "utf-8");
		params.put("notify_url", ALIPAY_NOTIFY_URL);
		params.put("sign_type", "RSA2");
		params.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		params.put("version", "1.0");
		JSONObject bizContentObj=new JSONObject();
		if(StringUtils.isNotEmpty(sdata.getString("body"))){
	    	bizContentObj.put("body", sdata.getString("body"));
	    }
		if(StringUtils.isNotEmpty(sdata.getString("subject"))){
	    	bizContentObj.put("subject", sdata.getString("subject"));
	    }
		/*if(StringUtils.isNotEmpty(sdata.getString("out_trade_no"))){
	    	bizContentObj.put("out_trade_no", sdata.getString("out_trade_no"));
	    }*/
		//目前取得是宝马订单orderid
		if(StringUtils.isNotEmpty(payOrderNo)){
	    	bizContentObj.put("out_trade_no", payOrderNo);
	    }
		//计算订单超时时间
		//设置未付款支付宝交易的超时时间，一旦超时，该笔交易就会自动被关闭。当用户进入支付宝收银台页面（不包括登录页面），
		//会触发即刻创建支付宝交易，此时开始计时。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，
		//无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		Date tStart = null,tEnd=null;
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		try {
	    	if(StringUtils.isNotEmpty(sdata.getString("time_start"))){
	    		tStart = format.parse(sdata.getString("time_start"));		    	
		    }
	    	if(StringUtils.isNotEmpty(sdata.getString("time_expire"))){
	    		tEnd = format.parse(sdata.getString("time_expire"));		    	
	    	}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(tEnd != null && tStart != null){
			long diff = tEnd.getTime() - tStart.getTime();
			long tOutExpress = diff/1000/60;
			bizContentObj.put("timeout_express", tOutExpress+"m");			
		}
		if(StringUtils.isNotEmpty(sdata.getString("total_amount"))){
	    	bizContentObj.put("total_amount", Double.parseDouble(sdata.getString("total_amount"))+"");
	    }
		bizContentObj.put("product_code", "QUICK_MSECURITY_PAY");
		if(StringUtils.isNotEmpty(sdata.getString("attach"))){
			bizContentObj.put("passback_params", sdata.getString("attach"));			
		}
		
		params.put("biz_content", bizContentObj.toJSONString());
		String asciiString = StringUtil.linkString(params, "&");
		logger.info(asciiString);
			
		String sign = AlipaySignature.rsa256Sign(asciiString, rsaCredential.getCredential(), Contants.CHARSET);		
		System.out.println();
		System.out.println("sign="+sign);
		System.out.println();
		params.put("sign", sign);
		
		
		//最后对请求字符串的所有一级value（biz_content作为一个value）进行encode，
		//编码格式按请求串中的charset为准，没传charset按UTF-8处理
		Map<String,Object> encodeParams = new HashMap<String,Object>();
		for(String key : params.keySet()){
			String val = (String)params.get(key);
			val = java.net.URLEncoder.encode(val, Contants.CHARSET);
			encodeParams.put(key, val);
		}
		asciiString = StringUtil.linkString(encodeParams, "&");
		logger.info("对请求字符串进行encode\n"+asciiString);
		String rData = null;
		try {
			 rData=SecurityUtil_AES.encrypt(asciiString, aesCredential.getCredential());
			 logger.info("输出的加密数据\n"+rData);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return rData;
	}


	/**订单查询
	 * @return
	 */
	@RequestMapping("/orderquery")
	@ResponseBody
	public Map<String, Object> orderQuery(HttpServletRequest request, HttpServletResponse response){		
		Map<String, Object> returnMap = new HashMap<String,Object>();
		logger.info("开始进入‘查询订单’方法 ["+request.getRequestURI()+"]");
		MessageHeader messageheader = (MessageHeader) request.getAttribute("messageheader");
		JSONObject sdata =JSONObject.parseObject((String)request.getAttribute("sdata"));
	    
		logger.info(messageheader);
		logger.info(sdata);
		
		String outTradeNo = sdata.getString("out_trade_no");
		if(StringUtils.isEmpty(outTradeNo)){
			logger.error("解析商户订单编号参数出错");
			returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new InvalidParamException("商户订单编号参数异常"))));
			return returnMap;
		}
		try {
			Orders orders = getwayService.orderQuery(outTradeNo);
			//将Orders转换成网关协议实体
			Map<String,Object> gwOrders = ordersToGWOrders(orders);
			returnMap.put("rdata", gwOrders);
			
			MessageHeader resultMsgHeader = new MessageHeader();
			resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
			returnMap.put("messageheader", resultMsgHeader);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new Exception())));
			return returnMap;
		}	
		logger.info("结束‘订单查询’方法 ["+request.getRequestURI()+"]");
		return returnMap;		
	}
	
	private Map<String, Object> ordersToGWOrders(Orders orders) {
		if(orders == null){
			return new HashMap<String,Object>();
		}
		Map<String, Object> gwOrders = new HashMap<String,Object>();
		gwOrders.put("out_trade_no", orders.getOutTradeNo());
		gwOrders.put("prepay_id", orders.getPrepayId());
		gwOrders.put("total_amount", orders.getTotalAmount());
		gwOrders.put("pay_tool_type", orders.getPaytooltype());
		gwOrders.put("subject", orders.getSubject());
		gwOrders.put("body", orders.getBody());
		gwOrders.put("goods_detail", orders.getGoodsDetail());
		gwOrders.put("attach", orders.getAttach());
		gwOrders.put("spbill_create_ip", orders.getSpbillCreateIp());
		gwOrders.put("trade_type", orders.getTradeType());
		
		JSONObject orderStatusJson = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		orderStatusJson.put("out_trade_no", orders.getOutTradeNo());
		orderStatusJson.put("pay_status", orders.getPaystatus());
		orderStatusJson.put("order_status", orders.getOrderstatus());
		orderStatusJson.put("update_time", sdf.format(orders.getUpdatetime()));
		gwOrders.put("order_status", orderStatusJson.toJSONString());	
		
		return gwOrders;
	}


	/**关闭订单
	 * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
	 * @return
	 */
	@RequestMapping("/closeorder")
	@ResponseBody
	public Map<String, Object> closeorder(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> returnMap = new HashMap<String,Object>();
		logger.info("开始进入‘关闭订单’方法 ["+request.getRequestURI()+"]");
		MessageHeader messageheader = (MessageHeader) request.getAttribute("messageheader");
		JSONObject sdata =JSONObject.parseObject((String)request.getAttribute("sdata"));
	    
		logger.info(messageheader);
		logger.info(sdata);
		String outTradeNo = sdata.getString("out_trade_no");
		if(StringUtils.isEmpty(outTradeNo)){
			logger.error("解析商户订单编号参数出错");
			returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new InvalidParamException("商户订单编号参数异常"))));
			return returnMap;
		}
		int payToolType = sdata.getInteger("pay_tool_type");
		/*
		 * 根据第三方支付工具类型去调用不同接口关闭订单
		 * 第三方支付工具类型:1支付宝、2微信支付
		 */		
		if(payToolType == 2){
			String httpResult = invokingWeixinAPICloseOrder(outTradeNo);
			
			if(StringUtils.isEmpty(httpResult)){
				logger.error("调用微信关闭订单接口出现错误");
				returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new InvokingWeiXinAPIException())));
				return returnMap;
			}
			Map<String,String> map = XMLUtil.readStringXmlOut(httpResult);
			String returnCode = map.get("return_code");
			String resultCode = map.get("result_code");
			
			if("FAIL".equals(returnCode)){
				logger.error("调用微信关闭订单接口出现错误:"+map);
				returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new InvokingWeiXinAPIException())));
				return returnMap;
			}
			if("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)){
				try {
					Thread thread = new CloseOrderThread(outTradeNo, getwayService);
					thread.start();				
				} catch (Exception e) {
					logger.error("执行对数据库关闭订单时出错");
					returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new Exception())));
					return returnMap;
				}
				MessageHeader resultMsgHeader = new MessageHeader();
				resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
				returnMap.put("messageheader", resultMsgHeader);
				
				Map<String,Object> rDataMap = new HashMap<String,Object>();				
				rDataMap.put("out_trade_no", outTradeNo);
				
				returnMap.put("rdata", rDataMap);
			}
			
		}
		/*
		 * 调用支付宝关闭订单接口处理过程
		 * 第三方支付工具类型:1支付宝、2微信支付
		 */		
		else if(payToolType == 1){
			//从数据库证书表读取RSA私钥
			Credential rsaPrivateCredential = credentialService.getCredential(FROM,TO, SignTypeEnum.RSA_PRIVATE.name());
			if(rsaPrivateCredential==null){
	        	logger.error(">>>关闭订单读取RSA私钥出错",new Exception());
	        	returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new RSAPrivateKeyException())));
				return returnMap;
	        }else if(StringUtils.isEmpty(rsaPrivateCredential.getCredential())){
	        	logger.error(">>>关闭订单读取RSA私钥出错",new Exception());
	        	returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new RSAPrivateKeyException())));
				return returnMap;
	        }
			//从数据库证书表读取RSA公钥
			Credential rsaPublicCredential = credentialService.getCredential(TO,FROM, SignTypeEnum.RSA_PUBLIC.name());
			if(rsaPublicCredential==null){
	        	logger.error(">>>关闭订单读取RSA公钥出错",new Exception());
	        	returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new RSAPrivateKeyException())));
				return returnMap;
	        }else if(StringUtils.isEmpty(rsaPublicCredential.getCredential())){
	        	logger.error(">>>关闭订单读取RSA公钥出错",new Exception());
	        	returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new RSAPrivateKeyException())));
				return returnMap;
	        }
			
			
			//发送http请求到支付宝支付，调用关闭订单接口			
			String appPrivateKey = rsaPrivateCredential.getCredential();
			String alipayPublicKey = rsaPublicCredential.getCredential();
			try {
				boolean aliResponse = invokingAlipayAPICloseOrder(outTradeNo,appPrivateKey,alipayPublicKey);
				if(!aliResponse){
					logger.error(">>>调用支付宝支付API时出错",new InvokingAlipayAPIException());
					returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new InvokingAlipayAPIException())));
					return returnMap;					
				}else{
					try {
						Thread thread = new CloseOrderThread(outTradeNo, getwayService);
						thread.start();				
					} catch (Exception e) {
						logger.error("执行对数据库关闭订单时出错");
						returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new Exception())));
						return returnMap;
					}
					
					MessageHeader resultMsgHeader = new MessageHeader();
					resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
					returnMap.put("messageheader", resultMsgHeader);
					
					Map<String,Object> rDataMap = new HashMap<String,Object>();				
					rDataMap.put("out_trade_no", outTradeNo);
					returnMap.put("rdata", rDataMap);
				}				
				
			} catch (AlipayApiException e) {
				logger.error(">>>调用支付宝支付API时出错",new InvokingAlipayAPIException());
				returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new InvokingAlipayAPIException())));
				return returnMap;
			}
			
		}
		logger.info("结束‘关闭订单’方法 ["+request.getRequestURI()+"]");
		return returnMap;		
	}

	/**
	 * 发送http请求到微信支付，调用关闭订单接口
	 * @param outTradeNo
	 * @return
	 */
	private String invokingWeixinAPICloseOrder(String outTradeNo) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("appid", WEIXIN_APPID);
		params.put("mch_id", WEIXIN_MCHID);
		params.put("out_trade_no", outTradeNo);
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		
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
		//调用关闭订单接口
		String xmlStr = XMLUtil.map2xmlBody(params, "xml");
		String httpResult = HttpUtil.postByXml(WEIXIN_CLOSE_ORDER_URL, xmlStr);
		
		return httpResult;
	}
	/**
	 * 发送http请求到支付宝支付，调用关闭订单接口
	 * @param outTradeNo
	 * @return
	 * @throws AlipayApiException 
	 */
	private boolean invokingAlipayAPICloseOrder(String outTradeNo,String APP_PRIVATE_KEY,String ALIPAY_PUBLIC_KEY) throws AlipayApiException {	
		AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATWAY_URL, ALIPAY_APPID, APP_PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLIC_KEY, "RSA2");
		AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
		StringBuffer sb = new StringBuffer();
					 sb.append("{").append("out_trade_no").append(":").append(outTradeNo).append("}");
		request.setBizContent("{\"out_trade_no\":\""+outTradeNo+"\"}");
		AlipayTradeCloseResponse response = alipayClient.execute(request);
		
		return response.isSuccess();
	}
	
	
	/**
	 * 调用微信接口进行下单
	 * @param sdata
	 * @return String
	 */
	private String payUnifiedOrder(JSONObject sdata,String payOrderNo)  {
		Map<String, Object> params = new HashMap<String, Object>();
		//add by 2017-11-21 新增微信扫码处理 start
		String trade_type = sdata.getString("trade_type");
        //下单地址
		String key = WEIXIN_MCH_API_SECRET;
		if("NATIVE".equals(trade_type)){
			params.put("appid", sdata.getString("appid"));
			params.put("mch_id", sdata.getString("mch_id"));	
			params.put("notify_url", sdata.getString("notify_url"));
			//拼接asciiString和商户密钥
			key = sdata.getString("api_key");
		}else{
			//add by 2017-11-21 新增微信扫码处理 end
			params.put("appid", WEIXIN_APPID);
			params.put("mch_id", WEIXIN_MCHID);
			params.put("notify_url", WEIXIN_NOTIFY_URL);
			//拼接asciiString和商户密钥
			}
		//add by 2017-11-21 新增微信扫码处理 end
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));		
		params.put("body", sdata.getString("body"));
		params.put("detail", "{\"goods_detail\":"+sdata.getString("goods_detail")+"}");
		params.put("out_trade_no", payOrderNo);//取得支付记录payOrderNo
		//params.put("out_trade_no", sdata.getString("out_trade_no"));
		//微信的总金额要转为int类型
		double d1 =  Double.parseDouble(sdata.getString("total_amount"));
		double d2 = FractionDigits.fractionDigits(d1*100, 0, false);
		BigDecimal totalAmount = new BigDecimal(d2);
		params.put("total_fee", totalAmount.toString());
		
		params.put("spbill_create_ip", sdata.getString("spbill_create_ip"));
		params.put("trade_type", sdata.getString("trade_type"));
		params.put("attach",sdata.getString("attach"));	
		params.put("time_start", sdata.getString("time_start"));
		params.put("time_expire",sdata.getString("time_expire"));
				
		//
		//生成微信签名，调用微信下单API接口前		
		//
		String asciiString = StringUtil.linkString(params, "&");
		//拼接asciiString和商户密钥
		asciiString +="&key="+key;
		//md5编码并转成大写
		String sign=SecurityUtils.getMd5Byte32(asciiString).toUpperCase();
		
		params.put("sign", sign);
		String xmlStr = XMLUtil.map2xmlBody(params, "xml");
		String httpResult = HttpUtil.postByXml(WEIXIN_UNIFIE_DORDER_URL, xmlStr);
		
		return httpResult;
	}
}
