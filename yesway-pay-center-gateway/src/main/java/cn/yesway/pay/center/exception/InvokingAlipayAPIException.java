package cn.yesway.pay.center.exception;

/**
 * 调用支付宝支付API接口出现异常
 *
 */
public class InvokingAlipayAPIException extends BaseException {
	private static final long serialVersionUID = -112492189895425437L;

    public InvokingAlipayAPIException() {
        super(BaseException.ErrCode.INVOKING_ALIPAY_API_EXCEPTION, "调用支付宝支付API接口出现异常");
    }
}
