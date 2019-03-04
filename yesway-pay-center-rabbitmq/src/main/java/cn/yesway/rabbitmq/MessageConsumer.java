package cn.yesway.rabbitmq;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.entity.Configuration;
import cn.yesway.pay.order.service.ConfigurationService;
import cn.yesway.pay.order.service.OrderService;
import cn.yesway.service.BmwOrderService;
import cn.yesway.v1.service.BmwOrderV1Service;

import com.rabbitmq.client.Channel;

/**
 * 功能概要：消费接收
 * 
 */
@Service
public class MessageConsumer implements ChannelAwareMessageListener {
	private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
	@Resource
	private AmqpTemplate amqpTemplate;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private OrderService orderService;
	@Autowired 
	private BmwOrderService bmwOrderService;
	@Autowired 
	private BmwOrderV1Service bmwOrderV1Service;
	
	private final String CONFIG_KEY = "sync_to_bmw_order_status";
	private final String CONFIG_VALUE = "on";
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		logger.info("receive message:{}",message);
		String receivedMessage=null;
		receivedMessage = new String(message.getBody());
		logger.info("message.body:{}",receivedMessage);
		
		MessageProperties prop = message.getMessageProperties();
		long deliveryTag = prop.getDeliveryTag();
		
		if(StringUtils.isEmpty(receivedMessage) || "{}".equals(receivedMessage)){
			logger.error(">>>MessageConsumer接收到消息为null");
			channel.basicAck(deliveryTag, true);
			return;
		}
		//第一：修改支付中心的订单表的状态
		int state1 = orderService.updateOrderStatusByMQMessage(receivedMessage);
		if(state1 == 0){
			logger.error(">>>MessageConsumer未更新订单状态");
			channel.basicAck(deliveryTag, true);
			return;
		}
		
		//第二：发送http请求，调用宝马订单管理系统网关接口修改订单状态
		//根据配置是否对oem进行消息推送
		JSONObject object = JSONObject.fromObject(receivedMessage);
		JSONObject msgheader = object.getJSONObject("messageheader");
		String oemId = msgheader.getString("oemid");
		Configuration config = configurationService.get(oemId, CONFIG_KEY);
		if(config==null){
			logger.error(">>>没有发现对调用宝马订单管理系统网关接口修改订单状态的配置");
			channel.basicAck(deliveryTag, true);
			return;
		}
		String configValue = config.getValue();
		if(configValue!=null && CONFIG_VALUE.equals(configValue)){
			logger.info(">>>同意同步订单到宝马网关:configValue="+configValue);
			int state2 = 0;
			try {
				state2 = bmwOrderV1Service.syncToBmwOrderStatus(object);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("回调失败" + e.getMessage());
				channel.basicAck(deliveryTag, true);
				return;
			}
			
			channel.basicAck(deliveryTag, true);
			logger.info(">>>队列消息已删除,Tag="+deliveryTag);
			/*if(state1 >0 && state2 >0){
				//删除队列中的消息
				MessageProperties prop = message.getMessageProperties();
				long deliveryTag = prop.getDeliveryTag();
				channel.basicAck(deliveryTag, true);
				logger.info(">>>队列消息已删除,Tag="+deliveryTag);
			}*/
		}
		
	}
	

}