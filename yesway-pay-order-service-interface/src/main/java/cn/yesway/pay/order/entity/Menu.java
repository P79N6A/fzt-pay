package cn.yesway.pay.order.entity;

import java.io.Serializable;

/**
 * @author 菜单表
 *  2017年2月16日上午9:43:07
 *  menu
 */
public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8719199063728369654L;

	/** 菜单编码. */
	private Integer menucode;

	/** 菜单导航地址. */
	private String menuurl;

	/** 父菜单. */
	private String parentcode;

	/** 叶子节点. */
	private Boolean isleaf;

	public Integer getMenucode() {
		return menucode;
	}

	public void setMenucode(Integer menucode) {
		this.menucode = menucode;
	}

	public String getMenuurl() {
		return menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl == null ? null : menuurl.trim();
	}

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode == null ? null : parentcode.trim();
	}

	public Boolean getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Boolean isleaf) {
		this.isleaf = isleaf;
	}
}