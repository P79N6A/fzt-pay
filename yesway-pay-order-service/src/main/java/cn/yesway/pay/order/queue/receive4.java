package cn.yesway.pay.order.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class receive4 {

	private static final String EXCHANGE_NAME = "logs";
      
	  /**存入队列
	 * @param argv1
	 * @throws Exception
	 */
	public static void main(String[] argv1) throws Exception {
		  ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("10.1.11.185");
			factory.setPort(5672);
			factory.setUsername("admin");
			factory.setPassword("admin");
			
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			String[] argv={"paytest","id:13","name:张11阿11三","age:20211"};//第一个存储的为“队列名称”，后面才是传输的数据
			String severity=getSeverity(argv);//队列名称
			String message = getMessage(argv);
			
			 channel.basicPublish("", severity, null, message.getBytes());
			 //channel.basicPublish("", routeKey, null, SerializationUtils.serialize(order));
			 System.out.println(" [x] Sent '" + severity + "':'" + message.toString() + "'");

			channel.close();
			connection.close();
	  }
	
	
	  private static String getSeverity(String[] strings){
		    if (strings.length < 1)
		            return "info";
		    return strings[0];
		  }
		private static String getMessage(String[] strings) {
			if (strings.length < 1)
				return "info: Hello World!";
			return joinStrings(strings, " ");
		}

		private static String joinStrings(String[] strings, String delimiter) {
			int length = strings.length;
			if (length == 0)
				return "";
			StringBuilder words = new StringBuilder(strings[0]);
			for (int i = 1; i < length; i++) {
				words.append(delimiter).append(strings[i]);
			}
			return words.toString();
		}
}
