package cn.yesway.rabbitmq;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yesway.rabbitmq.MessageProducer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class MessageProducerTest {
	@Autowired
	MessageProducer messageProducer;
	@Test
	public void testSendMessage() {
		/*for(int i=0;i<100;i++){
			messageProducer.sendMessage("Queue message-"+i);
		}*/
		String oemid = "1000";
		String mchid = "100001";
		String orderId = "10001000010020170524143006001573";
		String outTradeNo = "201705241430050";
		String tradeNo = "2017052421001004660235734583";//(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(10);
		
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
		messageProducer.sendMessage(JSON.toJSONString(msg));
	}

}
