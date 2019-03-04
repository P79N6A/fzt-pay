package cn.yesway.bmw.manage.entity;


public class Menu extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:数字，不重复     
     * @字段:menuCode INT(10)  
     */	
	private java.lang.Integer menuCode;

	/**
     * @备注:     
     * @字段:menuUrl VARCHAR(200)  
     */	
	private java.lang.String menuUrl;

	/**
     * @备注:     
     * @字段:parentCode VARCHAR(50)  
     */	
	private java.lang.String parentCode;

	/**
     * @备注:     
     * @字段:isLeaf BIT(0)  
     */	
	private java.lang.Boolean isLeaf;
	public Menu(){
	}

	public Menu(
		java.lang.Integer menuCode
	){
		this.menuCode = menuCode;
	}

	public void setMenuCode(java.lang.Integer menuCode) {
		this.menuCode = menuCode;
	}
	 
	public java.lang.Integer getMenuCode() {
		return this.menuCode;
	}
	public void setMenuUrl(java.lang.String menuUrl) {
		this.menuUrl = menuUrl;
	}
	 
	public java.lang.String getMenuUrl() {
		return this.menuUrl;
	}
	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
	}
	 
	public java.lang.String getParentCode() {
		return this.parentCode;
	}
	public void setIsLeaf(java.lang.Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	 
	public java.lang.Boolean getIsLeaf() {
		return this.isLeaf;
	}
}
