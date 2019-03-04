package cn.yesway.bmw.manage.service;

import java.io.Serializable;
import java.util.List;

import cn.yesway.bmw.manage.entity.Pager;

/**
 * service基础类
 */
public interface BaseService<T, PK extends Serializable> {

	/**
	 * 按主键查找对象
	 */
	T getById(PK pk);

	/**
	 * 获取总记录数
	 */
	int getTotalCount(T obj);

	/**
	 * 按实体对象属性动态查找列表
	 */
	List<T> findList(T obj);

	/**
	 * 分页
	 */
	Pager findPageList(T obj, Integer pageNumber, Integer pageSize);

	/**
	 * 保存
	 */
	int save(T obj);

	/**
	 * 更新
	 */
	int update(T obj);
	

	/**
	 * 删除
	 */
	int delete(PK pk);

}
