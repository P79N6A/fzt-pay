package cn.yesway.pay.center.exception;

/**
 * 无法识别商户身份
 *
 */
public class MerchantIdentityException extends BaseException {
	private static final long serialVersionUID = -112492189895425437L;

    public MerchantIdentityException() {
        super(BaseException.ErrCode.UNRECOGNIZED_MCH_IDENTITY, "无法识别商户身份");
    }
}
