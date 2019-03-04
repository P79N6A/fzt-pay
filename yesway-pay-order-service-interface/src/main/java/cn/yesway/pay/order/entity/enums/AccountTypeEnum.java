package cn.yesway.pay.order.entity.enums;
/**
 * 账户表的账户类型
 * 1支付宝账户，2微信账户，3oem账户，4商户账户
 *
 */
public enum AccountTypeEnum {
	Alipay(1),WEIXIN(2),OEM(3),MCH(4);
	public int number;
	private AccountTypeEnum(int number){
		this.number=number;
	}
	public int getNumber() {
		return number;
	}
	
}
