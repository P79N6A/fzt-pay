
package cn.yesway.bmw.manage.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 用来存储用户登录成功后所需要的信息
 *  2017年6月29日上午9:45:10
 *  UserProfile
 */
public class UserProfile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5688677278308976623L;

	/**
	 * 用户的所有资源信息
	 */
	private List<MgtMenu> moduleList;

	public List<MgtMenu> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<MgtMenu> moduleList) {
		this.moduleList = moduleList;
	}
	
	
}
