package cn.yesway.pay.order.queue;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 1、声明名为“logs”的exchange的，方式为"fanout"，和发送端一样。
 * 
 * 2、channel.queueDeclare().getQueue();该语句得到一个随机名称的Queue，该queue的类型为non-durable、
 * exclusive、auto-delete的，将该queue绑定到上面的exchange上接收消息。
 * 
 * 3、注意binding queue的时候，channel.queueBind()的第三个参数Routing
 * key为空，即所有的消息都接收。如果这个值不为空，在exchange type为“fanout”方式下该值被忽略！
 * 
 * @author y 2017年2月14日下午1:52:36 ReceiveLogs
 */
public class ReceiveLogs3 {

	private static final String EXCHANGE_NAME = "pay";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.1.11.185");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		//String queueName = "pay";
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());

			System.out.println(" [x] Received '" + message + "'");
		}
	}
}