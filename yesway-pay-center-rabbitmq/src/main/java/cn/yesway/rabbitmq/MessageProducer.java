package cn.yesway.rabbitmq;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

/**
 * 功能概要：消息产生,提交到队列中去
 * 
 */
@Service
public class MessageProducer {
	
	private Logger logger = LoggerFactory.getLogger(MessageProducer.class);

	@Resource
	private AmqpTemplate amqpTemplate;

	public void sendMessage(Object message){
	  logger.info("to send message:{}",message);
	  amqpTemplate.convertAndSend("YPCRoutingKey",message);
	}
}