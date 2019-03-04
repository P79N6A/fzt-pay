package cn.yesway.pay.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.dao.OEMDao;
import cn.yesway.pay.order.entity.OEM;
@Service
public class OemServiceImpl implements OemService {

	@Autowired
	private OEMDao oemDao;
	
	
	@Override
	public OEM getById(String oemId) {		
		return oemDao.selectByPrimaryKey(oemId);
	}




}
