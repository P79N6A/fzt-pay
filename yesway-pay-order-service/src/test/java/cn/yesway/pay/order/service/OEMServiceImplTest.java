package cn.yesway.pay.order.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yesway.pay.order.entity.OEM;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class OEMServiceImplTest {

	@Autowired
	private OemService oemService;
	@Test
	public void testGetById() {
		OEM oem = oemService.getById("02864ccf-9745-43c9-a975-1bc182277192");
		Assert.assertNotNull(oem);
	}

}
