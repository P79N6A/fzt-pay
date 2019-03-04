package cn.yesway.pay.order.rabbitmq;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yesway.pay.order.dao.OrderPayStatusDao;
import cn.yesway.pay.order.dao.OrdersDao;
import cn.yesway.pay.order.entity.OrderPayStatus;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.utils.ConfigUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**测试类
 * @author 1、队列中取出数据，然后修改orders订单表支付状态，然后把获得信息insert订单状态表 2017年3月6日上午10:46:26
 *         OrderQueue
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class OrderQueue {

	private final Logger logger = Logger.getLogger(this.getClass());
	private static final String TASK_QUEUE_NAME = ConfigUtil.getProperty("Queue_Name");
	private static final String Host_Ip = ConfigUtil.getProperty("Host_Ip");
	private static final String Port = ConfigUtil.getProperty("Port");
	private static final String Username = ConfigUtil.getProperty("Username");
	private static final String Password = ConfigUtil.getProperty("Password");

	@Autowired
	OrdersDao ordersDao;

	@Autowired
	OrderPayStatusDao orderPayStatusDao;

	@Test
	public void test() throws IOException, TimeoutException,ShutdownSignalException, ConsumerCancelledException,InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(Host_Ip);
		factory.setPort(Integer.parseInt(Port));
		factory.setUsername(Username);
		factory.setPassword(Password);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		channel.basicQos(1);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(TASK_QUEUE_NAME, true, consumer);
		while (true) {
			@SuppressWarnings("deprecation")
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
			JSONObject object = JSONObject.fromObject(message);
			String outTradeNo = (String) object.get("out_trade_no");
			String payStatus = (String) object.get("payStatus");
			String tradeNo = (String) object.get("trade_no");
			System.out.println("接收到..队列数据商户订单号是outTradeNo:" + outTradeNo);
			Orders order = new Orders();
			OrderPayStatus orderPay = new OrderPayStatus();
			// 1、修改订单表支付状态
			order.setOutTradeNo(outTradeNo);
			order.setOrderstatus(1);// 订单状态
			order.setPaystatus(1);// 支付状态
			order.setTradeNo(tradeNo);
			order.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			int record = ordersDao.updateStatusByCode(order);
			if (record > 0) {
				logger.info("\n 更新订单Orders表成功!");
			} else {
				logger.error("\n 更新订单Orders表失败!");
			}
			// 2、insert订单状态表
			orderPay.setId(UUID.randomUUID().toString());
			orderPay.setOutTradeNo(outTradeNo);
			if (StringUtils.isNotEmpty(payStatus)) {
				orderPay.setPaystatus(Integer.valueOf(payStatus));
			}
			orderPay.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			int result = orderPayStatusDao.insert(orderPay);
			if (result > 0) {
				logger.info("更新订单状态表成功");
			} else {
				logger.error("更新订单状态表失败");
			}
			System.out.println("输出返回信息是:" + result);
			/*
			 * doWork(message); System.out.println(" [x] Done");
			 * channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			 */
		}

	}
}
