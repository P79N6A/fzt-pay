package cn.yesway.pay.center.exception;

/**
 * 无法失败商户身份
 *
 */
public class OEMIdentityException extends BaseException {
	private static final long serialVersionUID = -112492189895425437L;

    public OEMIdentityException() {
        super(BaseException.ErrCode.UNRECOGNIZED_MCH_IDENTITY, "无法识别OEM身份");
    }
}
