package cn.yesway.pay.order.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;
import cn.yesway.pay.order.dao.OrderPayStatusDao;
import cn.yesway.pay.order.entity.OrderPayStatus;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 
 * 功能概要：读取队列的程序端，实现了Runnable接口
 * 
 */
public class Receiver extends EndPoint implements Runnable, Consumer {
	
	@Autowired
	private OrderPayStatusDao orderPayStatusDao;

	private static int index = 0;
	public Receiver(String routeKey) throws IOException, TimeoutException {
		super(routeKey);
	}

	/*public void run() {
		try {
			// start consuming messages. Auto acknowledge messages.
			channel.basicConsume(routeKey, true, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public void run() {
		try {
			
			// start consuming messages. Auto acknowledge messages.
			QueueingConsumer consumer = new QueueingConsumer(channel); 
			channel.basicConsume(routeKey, true, consumer);
			while(true){
				  QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		          String message = new String(delivery.getBody());  
		          System.out.println(" [x] Received '" + message + "'");
		          JSONObject object = JSONObject.fromObject(message);
		          String outTradeNo=(String) object.get("orderId");
		          String payStatus=(String) object.get("outOrderId");
		          System.out.println("获得队列数据商户订单号是outTradeNo:"+outTradeNo);
		          OrderPayStatus orderPay = new OrderPayStatus();
		         int record = orderPayStatusDao.insert(orderPay);
			}
		} catch (IOException | ShutdownSignalException | ConsumerCancelledException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Called when consumer is registered.
	 */
	public void handleConsumeOk(String consumerTag) {
		System.out.println("Consumer " + consumerTag + " registered");
	}

	/**
	 * Called when new message is available.
	 */
	public void handleDelivery(String consumerTag, Envelope env,
			BasicProperties props, byte[] body) throws IOException {
		index++;
		System.out.println(index);
		//Map map = (HashMap) SerializationUtils.deserialize(body);
//		System.out.println("Message Number " + map.get("message number")
//				+ " received.");

	}

	public void handleCancel(String consumerTag) {
		System.out.println("handleCancel");
	}

	public void handleCancelOk(String consumerTag) {
		System.out.println("handleCancelOk");
	}

	public void handleRecoverOk(String consumerTag) {
		System.out.println("handleRecoverOk");
	}

	public void handleShutdownSignal(String consumerTag,
			ShutdownSignalException arg1) {
		System.out.println("handleShutdownSignal");
	}

	public static void main(String[] args) throws IOException, TimeoutException {
		Receiver consumer = new Receiver("java");
		//consumer.run();
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
	}
}