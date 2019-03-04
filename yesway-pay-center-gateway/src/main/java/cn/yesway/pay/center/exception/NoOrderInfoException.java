package cn.yesway.pay.center.exception;

public class NoOrderInfoException extends BaseException{

	/**
	 * 查询无此订单信息
	 */
	private static final long serialVersionUID = 3675127767631371266L;

	public NoOrderInfoException() {
		super(BaseException.ErrCode.NO_ORDER_INFO, "查询无此订单信息");
	}

}
