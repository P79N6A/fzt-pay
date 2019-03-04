package cn.yesway.utils;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class ClientCustomSSLTest {
	private Logger logger = LoggerFactory.getLogger(ClientCustomSSLTest.class);
	
	
	@Value("#{rabbitmqConfig.SYNC_TO_BMW_URL}")
	private String SYNC_TO_BMW_URL;
	@Value("#{rabbitmqConfig.CERT_P12_FILE}")
	private String CERT_P12_FILE;
	@Value("#{rabbitmqConfig.CERT_PASSWORD}")
	private String CERT_PASSWORD;
	
	@Test
	public void testDoSyncBySSL() {
		String orderId = "10001000010020170517114202899484";
		String outTradeNo = "201705171138024";
		String tradeNo = (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(10);
		
		JSONObject sdata = new JSONObject();
		sdata.put("orderid", orderId);
		sdata.put("out_trade_no", outTradeNo);
		sdata.put("trade_no", tradeNo);
		sdata.put("payStatus", "1");
		
		String dataJson = null;
		try {
			dataJson = ClientCustomSSL.doSyncBySSL(SYNC_TO_BMW_URL, sdata.toString(),CERT_P12_FILE,CERT_PASSWORD).toString();
			logger.info(dataJson);
		} catch (Exception e) {
			logger.error(">>>使用SSL访问宝马网关出错");
			e.printStackTrace();
		}
	}

}
