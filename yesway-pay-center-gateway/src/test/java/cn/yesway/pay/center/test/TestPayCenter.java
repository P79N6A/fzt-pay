
package cn.yesway.pay.center.test;

import java.util.Hashtable;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 支付的测试类
 *  2017年2月6日下午4:16:41
 *  TestPayCenter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class TestPayCenter {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	//cn.yesway.pay.order.service.QueueService queueService;
	
	//@Test
	public void test(){
		 //4、把数据写入队列
       	Map<String, String> params = new Hashtable<String, String>();
         	params.put("out_trade_no", "111");
       	    params.put("payStatus", "1");
       	    params.put("trade_no", "");
       	   JSONObject json = JSONObject.fromObject(params);
   	       logger.info("插入队列的数据params："+json.toString());
            /*boolean flag = queueService.insert(json.toString());
        	if(flag == true){
        		logger.info("支付宝回调信息写入队列成功");
        	}else{
        		logger.info("支付宝回调信息写入队列失败");
        	}*/
		
	}
	
}
