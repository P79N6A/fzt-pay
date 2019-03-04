
package cn.yesway.yccc.oem.service.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 定时任务小测试
 *  2017年6月19日下午4:46:49
 *  ExampleThreadTest
 */
public class ExampleThreadTest {

	public static void main(String[] args) {

		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
			ses.scheduleAtFixedRate(new Runnable(){
				public void run(){
	                 try {
	                	//System.out.println(System.currentTimeMillis() / 1000);
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 0, 2, TimeUnit.SECONDS);
	}

}
