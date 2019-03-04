package cn.yesway.v1.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.entity.PayCenterConfiguration;
import cn.yesway.pay.order.service.PayCenterConfigurationService;
import cn.yesway.utils.ClientHttpSSL;
import cn.yesway.utils.HttpUtils;
import cn.yesway.utils.MessageHeader;
import cn.yesway.utils.RSAUtils;
import cn.yesway.utils.SHADigestUtils;
import cn.yesway.utils.SecurityUtil_AES;
import cn.yesway.utils.SecurityUtils;

@Service
public class BmwOrderV1Service {
	private Logger logger = LoggerFactory.getLogger(BmwOrderV1Service.class);
	@Value("#{rabbitmqConfig.SYNC_TO_BMW_URL}")
	private String SYNC_TO_BMW_URL;
	
	@Value("#{rabbitmqConfig.clientKeyStore}")
	private String clientKeyStore;
	@Value("#{rabbitmqConfig.clientTrustKeyStore}")
    private String clientTrustKeyStore;
	@Value("#{rabbitmqConfig.clientKeyStorePassword}")
    private String clientKeyStorePassword;
	@Value("#{rabbitmqConfig.clientTrustKeyStorePassword}")
    private String clientTrustKeyStorePassword;
	@Value("#{rabbitmqConfig.caKeyPassword}")
    private String caKeyPassword;
	
	@Autowired
	private PayCenterConfigurationService payCenterConfigurationService;
	
	//同步宝马订单状态
	public Integer syncToBmwOrderStatus(JSONObject obj){
		if(obj==null){
			return 0;
		}
		
		JSONObject msgheader = obj.getJSONObject("messageheader");
		String oemId = msgheader.getString("oemid");
		String mchId = msgheader.getString("mchid");
		JSONObject sdata = (JSONObject)obj.get("sdata");
		int payToolType = sdata.getInt("payToolType");
		
		PayCenterConfiguration queryPayCenterConfig = new PayCenterConfiguration();
		queryPayCenterConfig.setMchId(mchId);
		queryPayCenterConfig.setOemId(oemId);
		queryPayCenterConfig.setPayToolType(payToolType);
		
		//查询具体的商户是否配置支付账号
		PayCenterConfiguration payCenterConfig = payCenterConfigurationService.findByCondition(queryPayCenterConfig);
		
		//如果商户没有配置自己的账号，则使用车厂自己的账号
		if(payCenterConfig == null){
			queryPayCenterConfig.setMchId("all");
			payCenterConfig = payCenterConfigurationService.findByCondition(queryPayCenterConfig);
		}
		
		if(payCenterConfig == null){
        	logger.error("回调支付状态查询配置信息");
        	return 0;
        }
		
		String notifyUrl = payCenterConfig.getNotityUrl();
		
		if(StringUtils.isEmpty(notifyUrl)){
			logger.error("商户没有配置回调地址不走回调逻辑！");
        	return 0;
		}
		//对支付中心回调请求第三方接口数据的加密
		String resData = payCenterToThirdData(obj,payCenterConfig.getAesKey(),payCenterConfig.getYeswayPublicKey());
		logger.info("支付中心回调第三方接口请求数据resData:"+resData);
		String dataJson = null;
		if(notifyUrl.startsWith("https://")){
			try {
				ClientHttpSSL sslHttp = new ClientHttpSSL(payCenterConfig.getNotifyClient(),payCenterConfig.getNotifyServer(),payCenterConfig.getNotifyClientPWD(),payCenterConfig.getNotifyServerPWD(),payCenterConfig.getNotifyClientPWD());
				dataJson = sslHttp.doSyncBySSL(payCenterConfig.getNotityUrl(), resData).toString();
				logger.info("使用https调用同步接口返回信息,dataJson:"+dataJson);
			} catch (Exception e) {
				logger.error(">>>使用SSL访问宝马网关出错");
				e.printStackTrace();
				return 0;
			}
		}else if(notifyUrl.startsWith("http://")){
			dataJson = HttpUtils.doPost(payCenterConfig.getNotityUrl(), resData, null,"UTF-8");
			logger.info("使用http调用同步接口返回信息,dataJson:"+dataJson);
		}
		
		if(StringUtils.isEmpty(dataJson)&&(!dataJson.startsWith("{")|| !dataJson.endsWith("}"))){
			return 0;
		}
		
		JSONObject resJson=JSONObject.fromObject(dataJson);
		logger.info(">>>收到向宝马同步订单状态回执:"+dataJson);
		
		String outTradeNo = sdata.getString("out_trade_no");
		
		String status = (String)resJson.get("rdata");
		if(status.equals("1")){
			logger.info(">>>商户订单outTradeNo:"+outTradeNo+"，同步宝马组合订单表状态成功");
		}else{
			logger.info(">>>商户订单outTradeNo:"+outTradeNo+"，同步宝马组合订单表状态失败");
		}
		
		return 1;		
	}
	
	
	/**
	 * 支付中心回调第三方接口拼装参数(加密)
	 * @param obj
	 * @param aesKey秘钥
	 * @param yeswayPublicKey  yesway自己的公钥（公钥加密）
	 * @return
	 */
	public String payCenterToThirdData(JSONObject obj,String aesKey,String yeswayPublicKey){
		logger.info("支付中心回调第三方接口拼装请求参数方法obj:"+obj);
		JSONObject msgheader = obj.getJSONObject("messageheader");
		String oemId = msgheader.getString("oemid");
		String mchId = msgheader.getString("mchid");
		int resultcode = msgheader.getInt("resultcode");
		JSONObject sdata = (JSONObject)obj.get("sdata");
		// 1 所有请求参数按参数名做字典序升序排列，将排序好的请求参数格式化成“参数名称”“参数值”的形式
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderid", sdata.getString("orderid"));
		params.put("payToolType", sdata.getInt("payToolType")+"");
		params.put("out_trade_no", sdata.getString("out_trade_no"));
		params.put("trade_no", sdata.getString("trade_no"));
		params.put("payStatus", sdata.getString("payStatus"));
		params.put("paidStartTime", sdata.getString("paidStartTime"));
//		params.put("paidEndTime", sdata.getString("paidEndTime"));
		params.put("paymentChannel", sdata.getString("paymentChannel"));
		params.put("amount",  sdata.getString("amount"));
		//2.1增加回调第三方接口附加数据字段
		params.put("attach",  sdata.getString("attach"));

		String data = SecurityUtils.createLinkString(params);
		logger.info("生成摘要的原始字符串：" + data);
		String sign = null;
		String sData = null;
		try {
			// 2 发送方（S）将待发送明文数据（Data）进行哈希算法（SHA1）得到数字摘要（SDigest）
			String sDigest = SHADigestUtils.encryptSHA(data);
			com.alibaba.fastjson.JSONObject stringA = new com.alibaba.fastjson.JSONObject();
			stringA.put("mchid", mchId);
			stringA.put("oemid", oemId);
			stringA.put("sdigest", sDigest);
			// 4 使用RSA算法和接收方（R）公钥（RPublicKey）对签名明文（StringA）进行加密，得到数字签名（Sign）
			sign = RSAUtils.encryptByPublicKey(stringA.toJSONString(),yeswayPublicKey);
			// 5 将使用AES128算法和对称密钥（SecretKey）加密明文数据（params），得到数据密文（SData）
			sData = com.alibaba.fastjson.JSONObject.toJSONString(params);
			logger.info("发送的明文原始字符串：" + sData);
			sData = SecurityUtil_AES.encrypt(sData, aesKey);
		} catch (Exception e) {
			logger.error("支付中心回调请求第三方接口请求参数加密失败!"+e.getMessage());
		}
		// 6 合并数据密文（SData）和数字签（Sign），得到待发送密文（SecretData）
		JSONObject secretData = new JSONObject();
		secretData.put("sdata", sData);
		secretData.put("sign", sign);
		// 7 将MessageHeader合并到发送密文（SecretData）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemId);
		messageheader.setMchid(mchId);
		messageheader.setResultcode(resultcode);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		messageheader.setTransactionid(transactionid);
		secretData.put("messageheader", messageheader);
		
		return secretData.toString();
		
	}
}
