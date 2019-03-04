package cn.yesway.pay.order.entity.enums;


public enum OrderStatusEnum {
	Unfinished(0,"未完成"),
	Completed(1,"已完成"),
	Closed(2,"已关闭");
	
	public int code;
	public String text;
	private OrderStatusEnum(int code,String text){
		this.code=code;
		this.text=text;
	}
	/**
	 * 根据值获取订单状态
	 * @param value
	 * @return
	 */
	public static String getText(int code){
		for(OrderStatusEnum c : OrderStatusEnum.values()){
			if(c.code == code){
				return c.text;
			}
		}
		return null;
	}
	public int getCode() {
		return code;
	}
	public String getText() {
		return text;
	}
	
}
