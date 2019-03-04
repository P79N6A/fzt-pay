package cn.yesway.pay.center.exception;

/**
 * 对称密钥失效或错误
 *
 */
public class SymmetricKeyException extends BaseException {
	private static final long serialVersionUID = -112492189895425437L;

    public SymmetricKeyException() {
        super(BaseException.ErrCode.SYMMETRIC_KEY_FAILURE_OR_ERROR, "对称密钥失效或错误");
    }
}
