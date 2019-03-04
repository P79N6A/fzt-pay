
package cn.yesway.test;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yesway.pay.order.dao.MerchantDao;
import cn.yesway.pay.order.dao.OrdersDao;
import cn.yesway.pay.order.entity.Merchant;
import cn.yesway.pay.order.entity.Orders;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class Test1 {

	@Autowired
	MerchantDao merchantDao;
	@Autowired
	OrdersDao ordersDao;
	//@Test
	public void test1(){
		Orders order = new Orders();
		order.setOrderid("11");
		order.setOutTradeNo("22");
		//order.setTradeNo("33");
		order.setOemid("444");
		order.setMchid("555");
		order.setCreatetime(new Timestamp(System.currentTimeMillis()));
		order.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		order.setSpbillCreateIp("10.1.2.128");
		order.setTotalAmount(0.1);
		int record=ordersDao.insertSelective(order);
		System.out.println("输出:"+record);
	}
	@Test
	public void test2(){
		Merchant record =new Merchant();
		record.setMchid("6FB0645B-9CC6-4754-A6A8-D251ACA95F0C");
		record.setMchname("BMW");
		record.setCreatetime(new Timestamp(System.currentTimeMillis()));
		record.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		record.setOemid("02864ccf-9745-43c9-a975-1bc182277192");
		record.setCreater("ghk");
		int a=merchantDao.insertSelective(record);
		if(a>0){
			System.out.println("success");
		}else{
			System.out.println("failed...");
		}
	}
}
