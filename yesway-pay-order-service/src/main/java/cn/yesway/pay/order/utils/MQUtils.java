package cn.yesway.pay.order.utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component("mqutils")
public class MQUtils {
	private Logger logger = Logger.getLogger(MQUtils.class);
	private boolean isReady = false;
	@Value("#{rabbitMQConfig.mqaddr}")
	private String mqaddr;
	@Value("#{rabbitMQConfig.Virtual_Host}")
	private String virtualHost;
	@Value("#{rabbitMQConfig.routingKey}")
	private String routingKey;
	@Value("#{rabbitMQConfig.exName}")
	private String exName;
	@Value("#{rabbitMQConfig.Queue_Name}")
	private String queueName;
	@Value("#{rabbitMQConfig.Host_Ip}")
	private String hostIp;
	@Value("#{rabbitMQConfig.Port}")
	private String port;
	@Value("#{rabbitMQConfig.Username}")
	private String username;
	@Value("#{rabbitMQConfig.Password}")
	private String password;
	
	private Channel channel;
	
	public MQUtils(String mqaddr,String virtualHost, String exName, String routingKey,String queueName,String hostIp,String port,String username,String password){
		this.mqaddr = mqaddr;
		this.routingKey = routingKey;
		this.virtualHost = virtualHost;
		this.exName = exName;
		this.queueName = queueName;
		this.hostIp = hostIp;
		this.port = port;
		this.username = username;
		this.password = password;
		if(hasNull(hostIp,queueName,exName) || "".equals(hostIp.trim()) || "".equals(queueName.trim())){
			System.out.println(">> OrderService RabbitMQ init ERROR !!!!! Invalid param is null");
			isReady=false;
		} else {
			channelInit();
		}
	}
	
	public MQUtils() {
	}

	public void channelInit(){
		try {
			/* 创建连接工厂 */
			ConnectionFactory factory = new ConnectionFactory();
			/* 设置连接地址 */
			factory.setUri(this.mqaddr);
			factory.setHost(hostIp);
			factory.setPort(Integer.valueOf(port).intValue());
			factory.setUsername(username);
			factory.setPassword(password);
			if(StringUtils.isNotEmpty(virtualHost)){
				factory.setVirtualHost(virtualHost);				
			}
			/* 建立新的连接 */
			Connection connection = factory.newConnection();
			/* 创建信道 */
			channel = connection.createChannel();
			isReady = true;
		} catch (Exception e) {
			isReady = false;
			logger.error("队列MQ初始化失败！"+e.getMessage());
		}
	}
	
	public boolean hasNull(Object ... str){
		for(Object s : str){
			if(s == null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 向队列发送消息
	 * @param content 
	 * @param e
	 * @return
	 * @throws  
	 */
	public boolean putExceptionMessage(String str) {
		logger.info("队列Service方法初始化....");
		if(!isReady || str == null){
			logger.info("初始化队列true与false："+isReady+",队列名称："+queueName);
			return false;
		}
		try {
			logger.info("队列IP:"+hostIp+";"+"队列名称:"+queueName+",初始化成功...");
			channel.basicPublish(exName, queueName, null, str.getBytes());
			return true;
		} catch (Exception e) {
			logger.error("MQ队列："+queueName+"初始化失败！"+e.getMessage());
			return false;
		}
	}

	public String getMqaddr() {
		return mqaddr;
	}

	public void setMqaddr(String mqaddr) {
		this.mqaddr = mqaddr;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	public String getExName() {
		return exName;
	}

	public void setExName(String exName) {
		this.exName = exName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
