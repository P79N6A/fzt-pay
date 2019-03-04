package cn.yesway.pay.order.queue;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import cn.yesway.pay.order.entity.Orders;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class send4 extends EndPoint{

  public send4(String routeKey) throws IOException, TimeoutException {
		super(routeKey);
		// TODO Auto-generated constructor stub
	}

private static final String EXCHANGE_NAME = "pay";

  public static void main(String[] argv1) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("10.1.11.185");
    //factory.setVirtualHost("Test");
    factory.setPort(5672);
    factory.setUsername("admin");
    factory.setPassword("admin");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
   // channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    String[] argv={"order", "adadfasfsafd444", "444", "444"};//第一个存储的为“队列名称”，后面才是传输的数据
    String severity = getSeverity(argv);//队列名称
    String message = getMessage(argv);
    //String routeKey = "OrderQueue";//队列名称
    
    Map<String,String> ss=new HashMap<String,String>();
    //MessageHeader s=new MessageHeader();
    Orders o=new Orders();
    //SerializationUtils.serialize(o);
    
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
