package cn.yesway.bmw.manage.entity;


public class MgtMenu extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:菜单Id     
     * @字段:menuId VARCHAR(36)  
     */	
	private java.lang.Integer menuId;

	/**
     * @备注:菜单名     
     * @字段:menuName VARCHAR(50)  
     */	
	private java.lang.String menuName;

	/**
     * @备注: 操作配置状态 0：正常，1：停用     
     * @字段:status INT(10)  
     */	
	private java.lang.Integer status;

	/**
     * @备注:父菜单id，一级菜单为空     
     * @字段:parentId VARCHAR(36)  
     */	
	private java.lang.String parentId;

	/**
     * @备注:菜单URL     
     * @字段:parentMenuUrl VARCHAR(100)  
     */	
	private java.lang.String parentMenuUrl;

	/**
     * @备注:类型  0：目录，1：菜单，2：按钮     
     * @字段:type INT(10)  
     */	
	private java.lang.Integer type;

	/**
     * @备注:是否有子菜单（0：无，1：有）     
     * @字段:subMenu INT(10)  
     */	
	private java.lang.Integer subMenu;

	/**
     * @备注:菜单图标     
     * @字段:icon VARCHAR(50)  
     */	
	private java.lang.String icon;

	/**
     * @备注:排序     
     * @字段:sort INT(10)  
     */	
	private java.lang.Integer sort;
	
	private java.util.Date createTime;
	
	public MgtMenu(){
	}

	public MgtMenu(
		java.lang.Integer menuId
	){
		this.menuId = menuId;
	}

	public void setMenuId(java.lang.Integer menuId) {
		this.menuId = menuId;
	}
	 
	public java.lang.Integer getMenuId() {
		return this.menuId;
	}
	public void setMenuName(java.lang.String menuName) {
		this.menuName = menuName;
	}
	 
	public java.lang.String getMenuName() {
		return this.menuName;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	 
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}
	 
	public java.lang.String getParentId() {
		return this.parentId;
	}
	public void setParentMenuUrl(java.lang.String parentMenuUrl) {
		this.parentMenuUrl = parentMenuUrl;
	}
	 
	public java.lang.String getParentMenuUrl() {
		return this.parentMenuUrl;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	 
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setSubMenu(java.lang.Integer subMenu) {
		this.subMenu = subMenu;
	}
	 
	public java.lang.Integer getSubMenu() {
		return this.subMenu;
	}
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}
	 
	public java.lang.String getIcon() {
		return this.icon;
	}
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	 
	public java.lang.Integer getSort() {
		return this.sort;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
}
