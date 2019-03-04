package cn.yesway.pay.center.exception;


public class RequestMethodException extends BaseException {
    private static final long serialVersionUID = 1L;

    public RequestMethodException() {
        super(BaseException.ErrCode.REQUEST_TYPE_ERROR, "请求方式错误");
    }
}
