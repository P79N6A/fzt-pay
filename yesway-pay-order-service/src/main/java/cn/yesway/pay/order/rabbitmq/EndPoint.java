package cn.yesway.pay.order.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
 
/**
 * 
 * 功能概要： EndPoint类型的队列
 * 
 */
public abstract class EndPoint{
     
    protected Channel channel;
    protected Connection connection;
    protected String routeKey;
     
    public EndPoint(String routeKey) throws IOException, TimeoutException{
    	
         this.routeKey = routeKey;
         
         //Create a connection factory
         ConnectionFactory factory = new ConnectionFactory();
         
         //hostname of your rabbitmq server
         factory.setHost("10.1.11.185");
         //factory.setVirtualHost("Test");
         factory.setPort(5672);
         
         factory.setUsername("admin");
         factory.setPassword("admin");
         
         //getting a connection
         connection = factory.newConnection();
         
         //creating a channel
         channel = connection.createChannel();
         
         //declaring a queue for this channel. If queue does not exist,
         //it will be created on the server.
        // channel.queueDeclare(routeKey, false, false, false, null);
    }
     
     
    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     * @throws IOException
     * @throws TimeoutException 
     */
     public void close() throws IOException, TimeoutException{
         this.channel.close();
         this.connection.close();
     }
}