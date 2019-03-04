package cn.yesway.pay.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class QueueServiceImplTest {
	private Logger logger = Logger.getLogger(QueueServiceImplTest.class);
	@Autowired
	QueueService queueService;
	@Test
	public void testInsert() {
		String oemid = "1000";
		String mchid = "100001";
		String orderId = "10001000010020170524141512771302";
		String outTradeNo = "201705241415101";
		String tradeNo = (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(10);
		
		JSONObject msgheader = new JSONObject();
		JSONObject sdata = new JSONObject();
		Map<String,Object> msg = new HashMap<String,Object>();
		
		msgheader.put("oemid", oemid);
		msgheader.put("mchid", mchid);
		msgheader.put("resultcode", 0);
		msgheader.put("transactionid", tradeNo);
		msgheader.put("version", "1.0");
		msgheader.put("createtime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		sdata.put("orderid", orderId);
		sdata.put("out_trade_no", outTradeNo);
		sdata.put("trade_no", tradeNo);
		sdata.put("payStatus", "1");
		
		msg.put("messageheader", msgheader);
		msg.put("sdata", sdata);
		
        logger.info("插入队列的数据params："+JSON.toJSONString(msg));
        boolean flag = queueService.insert(JSON.toJSONString(msg));
		if(flag == true){
     		logger.info("支付宝回调信息写入队列成功");
     	}else{
     		logger.info("支付宝回调信息写入队列失败");
     	}
	}

}
