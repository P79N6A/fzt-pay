package cn.yesway.pay.order.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.dao.OrderPayStatusDao;
import cn.yesway.pay.order.dao.OrdersDao;
import cn.yesway.pay.order.utils.MQUtils;

@Service
public class QueueServiceImpl implements QueueService {
	private Logger logger = Logger.getLogger(QueueServiceImpl.class);
	@Autowired
	private MQUtils mqutils;
	@Autowired
	private OrdersDao orderDao;
	@Autowired
	private OrderPayStatusDao orderPayStatusDao;
	//写入队列
	public boolean insert(String object) {
        logger.info("进入队列Service方法...");
		return mqutils.putExceptionMessage(object);
	}
  
}
