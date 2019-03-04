package cn.yesway.pay.order.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yesway.pay.order.entity.Merchant;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class MerchantServiceImplTest {
	@Autowired
	private MerchantService merchantService;
	@Test
	public void testGetById() {
		Merchant m = merchantService.getById("6FB0645B-9CC6-4754-A6A8-D251ACA95F0C");
		Assert.assertNotNull(m);
	}

}
