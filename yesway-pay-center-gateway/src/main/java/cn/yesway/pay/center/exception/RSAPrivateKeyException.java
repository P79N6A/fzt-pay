package cn.yesway.pay.center.exception;

/**
 * 私钥证书失效或错误
 *
 */
public class RSAPrivateKeyException extends BaseException {
	private static final long serialVersionUID = -112492189895425437L;

    public RSAPrivateKeyException() {
        super(BaseException.ErrCode.PRIVATE_KEY_CERTIFICATE_INVALID_OR_INCORRECT, "私钥证书失效或错误");
    }
}
