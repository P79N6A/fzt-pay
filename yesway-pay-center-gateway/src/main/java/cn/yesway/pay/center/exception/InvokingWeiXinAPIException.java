package cn.yesway.pay.center.exception;

/**
 * 无法识别商户身份
 *
 */
public class InvokingWeiXinAPIException extends BaseException {
	private static final long serialVersionUID = -112492189895425437L;

    public InvokingWeiXinAPIException() {
        super(BaseException.ErrCode.INVOKING_WEIXIN_API_EXCEPTION, "调用微信API接口出现异常");
    }
}
