package cn.yesway.pay.order.exception;


public class InvalidRequestMethodException extends BaseException {
    private static final long serialVersionUID = 1L;

    public InvalidRequestMethodException() {
        super(-3, "请求方式错误");
    }
}
