package cn.yesway.pay.order.service;

import cn.yesway.pay.order.entity.Credential;

public interface CredentialService {
	public Credential getCredential(String from,String to,String signType) ;
}
