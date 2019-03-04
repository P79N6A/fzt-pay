
package cn.yesway.pay.order.test;

import org.junit.Test;

import cn.yesway.pay.order.rsa.PrivateRSA;
import cn.yesway.pay.order.rsa.PublicRSA;

/** 订单管理测试类
 * @author ghk
 *  2017年2月7日下午5:48:35
 *  TestOrder
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class TestOrder {
	
	String data="";
	String data2="";
	
	//@Test
	public String test() throws Exception{
		
		data=PrivateRSA.getInstance().encryptByPrivateKey("11111");
		 data2=PublicRSA.getInstance().decryptByPublicKey(data);
		 System.out.println("data2:"+data2);
		return data;
	}
	@Test
	public void test2(){
		String[] x={"2","4","6"};
		int result=0,y = 0;
		for(int i=0;i<x.length;i++){
		result=Integer.parseInt(x[i]);
		//result=result+;
		y+=result;
		System.out.println(""+y);
		}
		return;
		
	}
	@Test
	public void Test3(){
		
	}

}
