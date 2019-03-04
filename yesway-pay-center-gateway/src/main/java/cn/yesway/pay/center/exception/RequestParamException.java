package cn.yesway.pay.center.exception;

public class RequestParamException extends BaseException {
	private static final long serialVersionUID = -112492189895425437L;

    public RequestParamException() {
        super(BaseException.ErrCode.PARAMETER_EXCEPTION, "参数异常");
    }
}
