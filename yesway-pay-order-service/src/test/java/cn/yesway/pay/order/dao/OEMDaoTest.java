package cn.yesway.pay.order.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yesway.pay.order.entity.OEM;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class OEMDaoTest {

	@Autowired
	OEMDao oemDao;
//	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		OEM oem = new OEM();
		oem.setOemid(UUID.randomUUID().toString());
		oem.setOemname("BMW");
		oem.setCreater("King");
		oem.setCreatetime(new Date());
		oem.setUpdater("King");
		oem.setUpdatetime(new Date());
		int n = oemDao.insert(oem);
		Assert.assertEquals(1, n);
	}

//	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

//	@Test
	public void testSelectByPrimaryKey() {
		fail("Not yet implemented");
	}

//	@Test
	public void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented");
	}

//	@Test
	public void testUpdateByPrimaryKey() {
		fail("Not yet implemented");
	}

}
