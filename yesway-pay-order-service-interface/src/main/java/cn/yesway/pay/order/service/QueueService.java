package cn.yesway.pay.order.service;
import java.io.IOException;

import cn.yesway.pay.order.entity.OrderPayStatus;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * @author 队列接口
 *  2017年2月24日下午2:27:10
 *  QueueOrder
 */
public interface QueueService {

	/**
	 * 写入队列
	 * @param object
	 * @return
	 */
	public boolean insert(String object);
	
	/**
	 * 訂單状态表
	 * @param record
	 * @return
	 *//*
	public int insert(OrderPayStatus record);*/
	
}
