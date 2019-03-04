package cn.yesway.pay.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.dao.MerchantDao;
import cn.yesway.pay.order.entity.Merchant;
@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;
	@Override
	public Merchant getById(String mchId) {		
		return merchantDao.selectByPrimaryKey(mchId);
	}

}
