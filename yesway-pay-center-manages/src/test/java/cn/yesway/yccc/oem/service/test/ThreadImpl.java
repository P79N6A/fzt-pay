
package cn.yesway.yccc.oem.service.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 多线程测试
 *  2017年6月19日下午2:19:56
 *  ThreadTest
 */
public class ThreadImpl  {

//	private Logger log = LoggerFactory.getLogger(ThreadImpl.class);
	
	public static void main(String[] args) {

		//这里也有1000条数据数据需要更新，就分3个人来进行完成
		Thread t1=new ThreadTest("t1", 5);
		Thread t2=new ThreadTest1("t2", 5);
		//Thread t3=new ThreadTest("t3", 10);
		t1.start();
		t2.start();
		//t3.start();
	}
}
	class ThreadTest extends Thread{
		 private String name;
		 private int ts;
		 
		 public ThreadTest(String name, int ts){
			 this.name=name;
			 this.ts=ts;
		 }
		 
    public void run (){
			try {
				sleep(3000);
				for(int i=0;i<ts;i++){
					System.out.println( name+"\n 正在执行"+i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		System.out.println("名字叫"+name+"的线程开始休眠"+ts+"毫秒");
	}
 }
   //class2处理方法
    class ThreadTest1 extends Thread{
        private String name1;
	    private int ts1;
	 
	 public  ThreadTest1(String name1, int ts1){
		 this.name1=name1;
		 this.ts1=ts1;
	 }
	 
	 public void run(){
		 for(int i=0;i<ts1;i++){
				System.out.println( name1+"\n 正在执行"+i);
			}
	 }
}