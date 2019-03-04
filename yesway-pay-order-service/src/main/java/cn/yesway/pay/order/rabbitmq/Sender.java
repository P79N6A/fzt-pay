package cn.yesway.pay.order.rabbitmq;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;


/**
 * 
 * 功能概要：消息生产者
 * 
 */
public class Sender extends EndPoint {

	public Sender(String routeKey) throws IOException, TimeoutException {
		super(routeKey);
	}

	public void send(Serializable object) throws IOException {
		channel.basicPublish("", routeKey, null,SerializationUtils.serialize(object));
		
	}

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException, ExecutionException {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入开启线程个数：");
		int taskSize = sc.nextInt();
		System.out.println("请输每个线程循环次数：");
		int testNum = sc.nextInt();
		// 创建一个线程池
		ExecutorService pool = Executors.newFixedThreadPool(taskSize);
		// 创建多个有返回值的任务
		List<Future> list = new ArrayList<Future>();
		Sender sender = new Sender("Data");
		System.out.println("----程序开始运行----");
		Date date1 = new Date();
		for (int i = 0; i < taskSize; i++) {
			Callable c = new MyCallable(sender, i, testNum);
			// 执行任务并获取Future对象
			Future f = pool.submit(c);
			// System.out.println(">>>" + f.get().toString());
			list.add(f);
		}
		// 关闭线程池
		pool.shutdown();

		// 获取所有并发任务的运行结果
		for (Future f : list) {
			// 从Future对象上获取任务的返回值，并输出到控制台
			System.out.println(">>>" + f.get().toString());
		}

		Date date2 = new Date();
		// System.out.println("----程序结束运行----，程序运行时间【"
		// + (date2.getTime() - date1.getTime()) + "毫秒】");
		System.out.println("----程序结束运行----\r\n程序总共执行【" + taskSize * testNum
				+ "次】" + "运行时间【" + (date2.getTime() - date1.getTime()) + "毫秒】"
				+ "平均每秒发送【" + taskSize * testNum
				/ ((date2.getTime() - date1.getTime()) / 1000 + 1) + "条】");

	}
}