package cn.yesway.bmw.manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.yesway.bmw.manage.entity.MgtMenu;


/**
 * MgtMenuDao
 */
@Repository
public interface MgtMenuDao extends BaseDao<MgtMenu, java.lang.String> {

	List<MgtMenu> queryLoadInfo(List<String> powerids);
	
	MgtMenu updateMenuByMenuId(MgtMenu menu);
	
	MgtMenu  getInfoByMenuId(int  menuId);
	
	int getMaxValuefromMenu();
}
