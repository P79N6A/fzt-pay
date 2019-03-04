package cn.yesway.pay.order.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yesway.pay.order.entity.Credential;
import cn.yesway.pay.order.entity.enums.SignTypeEnum;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class CredentialDaoTest {
    
	@Autowired
	CredentialDao credentialDao;
	
//	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		Credential cred = new Credential();
		/*cred.setCredid(UUID.randomUUID().toString());
		cred.setCredname("AES加密秘钥");
		cred.setOwnership("02864ccf-9745-43c9-a975-1bc182277192");
		cred.setCredstatus(1);
		cred.setCredential("1234567890123456");
		cred.setSigntype(SignTypeEnum.AES.name());
		cred.setTimeexpire(new Date());
		cred.setCreater("King");
		cred.setCreatetime(new Date());
		cred.setUpdater("King");
		cred.setUpdatetime(new Date());*/
		
		cred.setCredid(UUID.randomUUID().toString());
		cred.setCredname("支付宝的RSA加密公钥");
		cred.setFrom("alipay");
		cred.setConsumer("YESWAY");
		cred.setCredstatus(1);
		cred.setCredential("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1r4F2QOONBnNvDle/w9RYO+LV6PimU0yiNrRB6k8jnFGQAdeuZNb++pyr4ak6B8KiyJEJbukTcCrAzNJyY0hXfsKxVT0R6E1WwJMksvV+iSK6Br3a/YScX7lOPUdiBw76kXTIQd3rWZNcye9se5In2bj5IXDOd8/I6qbsQ57Rra9b+HDRbV4wacR+ubofqCb8e1mQsK7GuDe0UkTKpHbb52fXLv0b11z5NkLD12bpBH6lSLDDI68trRzQCINxrRlDrnfTvsydBuvMpA4NhUiZXiZ3IlisvUeVB4nNKFlsBXU4y1k0+yjYSbs9XP3A/xqb1ezTMm22Qn2NOVtMnUWUQIDAQAB");
		cred.setSigntype(SignTypeEnum.RSA_PUBLIC.name());
		cred.setTimeexpire(new Date());
		cred.setCreater("King");
		cred.setCreatetime(new Date());
		cred.setUpdater("King");
		cred.setUpdatetime(new Date());
		credentialDao.insert(cred);
		
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

//	@Test
	public void testSelectCredential() {
		fail("Not yet implemented");
	}

}
