package cn.yesway.pay.order.entity.enums;

public enum PayStatusEnum {
	Unpaid( 0 , "未支付"),
	Paid( 1 , "已支付"),
	Failure( 2 , "支付失败");
	public int code;
	public String text;
	
	private PayStatusEnum(int code,String text){
		this.code = code;
		this.text = text;
	}
	/**
	 * 根据值获取支付状态
	 * @param value
	 * @return
	 */
	public static String getText(int value){
		for(PayStatusEnum c : PayStatusEnum.values()){
			if(c.code == value){
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
