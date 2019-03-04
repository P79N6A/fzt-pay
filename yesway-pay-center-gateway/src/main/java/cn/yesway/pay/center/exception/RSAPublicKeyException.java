package cn.yesway.pay.center.exception;

/**
 * 私钥证书失效或错误
 *
 */
public class RSAPublicKeyException extends BaseException {
	private static final long serialVersionUID = -112492189895425437L;

    public RSAPublicKeyException() {
        super(BaseException.ErrCode.PUBLIC_KEY_CERTIFICATE_INVALID_OR_INCORRECT, "公钥证书失效或错误");
    }
}
