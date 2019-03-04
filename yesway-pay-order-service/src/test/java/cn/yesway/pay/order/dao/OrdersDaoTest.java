




package cn.yesway.pay.order.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yesway.pay.order.entity.Orders;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class OrdersDaoTest {

	@Autowired
	OrdersDao orderDao;
	//@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	//@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	//@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByPrimaryKey() {
		String outTradeNo="10002017021413354";
		List<Orders> order =orderDao.selectByPrimaryKey(outTradeNo);
		System.out.println(""+order.toString());
	}

	//@Test
	public void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented");
	}

	//@Test
	public void testUpdateByPrimaryKey() {
		fail("Not yet implemented");
	}

	//@Test
	public void testOrderQuery() {
		fail("Not yet implemented");
	}

	//@Test
	public void testCloseOrder() {
		fail("Not yet implemented");
	}

	//@Test
	public void testQueryOrdersCount() {
		fail("Not yet implemented");
	}

	//@Test
	public void testUpdateStatusByCode() {
		fail("Not yet implemented");
	}

}
