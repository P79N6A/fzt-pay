
package cn.yesway.pay.order.queue;

import java.io.IOException;  
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  
import com.rabbitmq.client.ConsumerCancelledException;  
import com.rabbitmq.client.QueueingConsumer;  
import com.rabbitmq.client.ShutdownSignalException;  
  
/**
 * @author 从队列读取数据
 *  2017年2月24日上午11:20:28
 *  rece5
 */
public class rece5 {  
    private final static String QUEUE_NAME = "order";  
    public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("10.1.11.185");
        //factory.setVirtualHost("Test");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
          
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");  
        QueueingConsumer consumer = new QueueingConsumer(channel);  
        boolean autoAck = false;  
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);  
  
        while (true) {  
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
          String message = new String(delivery.getBody());  
          //确认消息已经收到  
          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);  
          System.out.println(" [x] Received '" + message + "'");  
          doWork(message);  
          System.out.println(" [x] Done");  
        }  
          
    }  
    private static void doWork(String message) throws InterruptedException {  
        for (char ch : message.toCharArray()) {  
            if (ch == '.') {  
                Thread.sleep(1000);  
            }  
        }  
    }  
}  
