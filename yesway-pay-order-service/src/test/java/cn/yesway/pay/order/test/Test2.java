package cn.yesway.pay.order.test;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import net.sf.json.JSONObject;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class Test2 {

	/**对列的处理
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws InterruptedException 
	 * @throws ConsumerCancelledException 
	 * @throws ShutdownSignalException 
	 */
	 public static void main(String[] argv1) throws IOException, TimeoutException{
		 
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.104.105");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("admin");
			
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//String[] argv={"ccc", "adadfasfsafd", "djftest5", "3"};//第一个存储的为“队列名称”，后面才是传输的数据
		 Map<String, String> params = new Hashtable<String, String>();
         params.put("out_trade_no", "201704271022064");
         params.put("payStatus", "1");
         params.put("trade_no", (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		//String severity = getSeverity(argv);//队列名称
		//String message = getMessage(argv);
		 //String data=JSON.toJSONString(params);
		 JSONObject object = JSONObject.fromObject(params);
		 String data = object.toString();
		 //String str =reqJson.toString();
		 channel.basicPublish("", "BMW.Pay.Status", null, data.getBytes());
		 //channel.basicPublish("", severity, null, message.getBytes());
		 System.out.println(" [x] Sent '" + "" + "':'" + data.getBytes() + "'");

		 channel.close();
		 connection.close();
	 }
 
	
	 private static void doWork(String task) throws InterruptedException {
		    for (char ch: task.toCharArray()) {
		      if (ch == '.') Thread.sleep(1000);
		    }
		  }
	
}
