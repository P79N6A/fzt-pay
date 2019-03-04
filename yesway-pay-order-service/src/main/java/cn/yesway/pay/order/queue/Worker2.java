package cn.yesway.pay.order.queue;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;
import cn.yesway.pay.order.dao.OrderPayStatusDao;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
  
public class Worker2 {

  private static final String TASK_QUEUE_NAME = "pay";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("10.1.11.185");
	factory.setPort(5672);
	factory.setUsername("admin");
	factory.setPassword("admin");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    //channel.basicQos(1);
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    
    while (true) {
      @SuppressWarnings("deprecation")
	  QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      System.out.println(" [x] Received '" + message + "'");
      JSONObject object = JSONObject.fromObject(message);
      Object outTradeNo=object.get("out_trade_no");
      System.out.println("接收到..队列数据商户订单号是outTradeNo:"+outTradeNo);
      /*doWork(message);
      System.out.println(" [x] Done");
      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);*/
    }         
  }
  
  private static void doWork(String task) throws InterruptedException {
    for (char ch: task.toCharArray()) {
      if (ch == '.') Thread.sleep(1000);
    }
  }
}