package cn.yesway.bmw.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.yesway.bmw.manage.dao.MgtMenuDao;
import cn.yesway.bmw.manage.entity.MgtMenu;
import cn.yesway.bmw.manage.service.MgtMenuService;

/**
 * 基本CURD操作在MybatisBaseServiceImpl中实现 否则自行声明接口，实现方法
 */
@Service
public class MgtMenuServiceImpl extends MybatisBaseServiceImpl<MgtMenu, java.lang.String> implements MgtMenuService {

	@Autowired
	private MgtMenuDao mgtMenuDao;
	
	public List<MgtMenu> queryLoadInfo(List<String> powerids) {
		return mgtMenuDao.queryLoadInfo(powerids);
	}
	public MgtMenu updateMenuByMenuId(MgtMenu menu) {
		// TODO Auto-generated method stub
		return null;
	}
	public MgtMenu getInfoByMenuId(int menuId) {
		return mgtMenuDao.getInfoByMenuId(menuId);
	}
	//获取最大id值
	public int getMaxValuefromMenu() {
		return mgtMenuDao.getMaxValuefromMenu();
	}
	
}
