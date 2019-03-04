package cn.yesway.pay.center.exception;


public class DataIntegrityException extends BaseException {
    private static final long serialVersionUID = -112492189895425437L;

    public DataIntegrityException() {
        super(BaseException.ErrCode.PLAINTEXT_EXCEPTION, "明文数据异常");
    }

}