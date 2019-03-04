package cn.yesway.service;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.yesway.utils.ClientHttpSSL;

@Service
public class BmwOrderService {
	private Logger logger = LoggerFactory.getLogger(BmwOrderService.class);
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
	//同步宝马订单状态
	public Integer syncToBmwOrderStatus(JSONObject obj){
		if(obj==null){
			return 0;
		}
//		String dataJson = HttpUtils.doPost(SYNC_TO_BMW_URL, obj.toString(), null,"UTF-8");
		String dataJson = null;
		try {
			ClientHttpSSL sslHttp = new ClientHttpSSL(clientKeyStore,clientTrustKeyStore,clientKeyStorePassword,clientTrustKeyStorePassword,caKeyPassword);
			dataJson = sslHttp.doSyncBySSL(SYNC_TO_BMW_URL, obj.toString()).toString();
			logger.info("调用同步接口返回信息,dataJson:"+dataJson);
		} catch (Exception e) {
			logger.error(">>>使用SSL访问宝马网关出错");
			e.printStackTrace();
			return 0;
		}
		if(StringUtils.isEmpty(dataJson)&&(!dataJson.startsWith("{")|| !dataJson.endsWith("}"))){
			return 0;
		}
		JSONObject resJson=JSONObject.fromObject(dataJson);
		logger.info(">>>收到向宝马同步订单状态回执:"+dataJson);
		
		JSONObject sdata = (JSONObject)obj.get("sdata");
		String outTradeNo = sdata.getString("out_trade_no");
		
		String status = (String)resJson.get("rdata");
		if(status.equals("1")){
			logger.info(">>>商户订单outTradeNo:"+outTradeNo+"，同步宝马组合订单表状态成功");
		}else{
			logger.info(">>>商户订单outTradeNo:"+outTradeNo+"，同步宝马组合订单表状态失败");
		}
		
		return 1;		
	}
}
