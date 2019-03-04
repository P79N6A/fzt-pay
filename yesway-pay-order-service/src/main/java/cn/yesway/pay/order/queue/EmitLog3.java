package cn.yesway.pay.order.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**发送消息到一个名为“logs”的exchange上，使用“fanout”方式发送，即广播消息，不需要使用queue，发送端不需要关心谁接收。
 * @author y
 *  2017年2月14日下午1:51:34
 *  EmitLog
 */
public class EmitLog3 {

  private static final String EXCHANGE_NAME = "logs";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("10.1.11.185");
    factory.setPort(5672);
    factory.setUsername("admin");
    factory.setPassword("admin");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    String[] argv1={"10.1.11.54:2181,10.1.11.55:2181,10.1.11.56:2181", "adadfasfsafd", "djftest5", "3"};
    //String[] xx= {"total_amount:100,subject":"手机","orderId":"001"};
    String message = getMessage(argv1);

    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
  
  private static String getMessage(String[] strings){
    if (strings.length < 1)
            return "info: Hello World!";
    return joinStrings(strings, " ");
  }
  
  private static String joinStrings(String[] strings, String delimiter) {
    int length = strings.length;
    if (length == 0) return "";
    StringBuilder words = new StringBuilder(strings[0]);
    for (int i = 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }
}
