package cn.yesway.pay.order.queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author ghk 2017年2月14日上午9:45:35 QueueCenter隊列处理中心
 */
@Controller
@RequestMapping("/queue")
public class QueueCenter {

	private final Logger logger = Logger.getLogger(this.getClass());
	private static final String EXCHANGE_NAME = "logs";

	/**
	 * 发送队列
	 * 
	 * @throws TimeoutException
	 * @throws IOException
	 */
	@RequestMapping("/sendQueue")
	public void sendQueue(String[] argv) throws IOException, TimeoutException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.1.11.185");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("admin");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		//String[] argv={"OrderQueue", "adadfasfsafd", "djftest5", "3"};//第一个存储的为“队列名称”，后面才是传输的数据
		String severity = getSeverity(argv);//队列名称
		String message = getMessage(argv);
		//String routeKey = "OrderQueue";//队列名称
		channel.basicPublish("", severity, null, message.getBytes());
		System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

		channel.close();
		connection.close();
	}

	private static String getSeverity(String[] strings){
	    if (strings.length < 1)
	            return "info";
	    return strings[0];
	  }

	  private static String getMessage(String[] strings){ 
	    if (strings.length < 2)
	            return "Hello World!";
	    return joinStrings(strings, " ", 1);
	  }
	  
	  private static String joinStrings(String[] strings, String delimiter, int startIndex) {
	    int length = strings.length;
	    if (length == 0 ) return "";
	    if (length < startIndex ) return "";
	    StringBuilder words = new StringBuilder(strings[startIndex]);
	    for (int i = startIndex + 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	  }

	
}
