

package cn.yesway.pay.order.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;



public class rabbitTest {

	private final static String EXCHANGE_NAME = "rabbitlogs";

	  public static void main(String[] argv) throws Exception {
	                
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("10.1.11.185");
		factory.setPort(Integer.parseInt("5672"));
		factory.setUsername("admin");
		factory.setPassword("admin");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare(EXCHANGE_NAME, "direct");

	    String severity = getSeverity(argv);
	    String message = getMessage(argv);

	    channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
	    System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

	    /*channel.basicPublish( "", TASK_QUEUE_NAME, 
	                MessageProperties.PERSISTENT_TEXT_PLAIN,
	                message.getBytes());*/
	    System.out.println(" [x] Sent '" + message + "'");
	    
	    channel.close();
	    connection.close();
	  }
	  private static String getSeverity(String[] strings){
		    if (strings.length < 1)
		            return "info";
		    return strings[0];
		  }
	  private static String getMessage(String[] strings){
		    if (strings.length < 1)
		      return "Hello World!";
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
