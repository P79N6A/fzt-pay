package cn.yesway.bmw.manage.service;

import java.util.List;

import cn.yesway.bmw.manage.entity.MgtMenu;


/**
 * MgtMenuService接口
 */
public interface MgtMenuService extends BaseService<MgtMenu, java.lang.String> {

	List<MgtMenu> queryLoadInfo(List<String> powerids);
	
	MgtMenu updateMenuByMenuId(MgtMenu menu);
	
	MgtMenu  getInfoByMenuId(int  menuId);
	//获取最大值
	int getMaxValuefromMenu();
}
