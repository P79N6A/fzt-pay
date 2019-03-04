package cn.yesway.pay.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.dao.CredentialDao;
import cn.yesway.pay.order.entity.Credential;
@Service
public class CredentialServiceImpl implements CredentialService{
	@Autowired
	private CredentialDao credentialDao;

	@Override
	public Credential getCredential(String from,String to,String signType) {
		return credentialDao.selectCredential(from,to, signType);
	}
}
