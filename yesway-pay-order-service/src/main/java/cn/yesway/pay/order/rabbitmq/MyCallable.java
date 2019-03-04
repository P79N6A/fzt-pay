package cn.yesway.pay.order.rabbitmq;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Object> {
	private int taskNum;
	private Sender sender;
	private int testNum;

	/**
	 * @param sender
	 * @param taskNum
	 * @param testNum
	 */
	MyCallable(Sender sender, int taskNum, int testNum) {
		this.taskNum = taskNum;
		this.sender = sender;
		this.testNum = testNum;
	}

	public Object call() throws Exception {
		// System.out.println(">>>" + taskNum + "任务启动");
		// Date dateTmp1 = new Date();
		for (int i = 1; i <= testNum; i++) {
			sender.send(new byte[100]);
			System.out.println(taskNum + "线程执行完成第【" + i + "次】");
		}

		// Date dateTmp2 = new Date();
		// long time = dateTmp2.getTime() - dateTmp1.getTime();
		// System.out.println(">>>" + taskNum + "任务终止");
		return taskNum + "线程执行完成";
	}
}
