package cn.yesway.pay.center.exception;


public class InvalidParamException extends BaseException {
    private static final long serialVersionUID = -112492189895425437L;

    public InvalidParamException(String errmsg) {
        super(BaseException.ErrCode.PARAMETER_EXCEPTION, errmsg);
    }

}