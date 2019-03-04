package cn.yesway.pay.order.exception;


public class UnrecognizedUserException extends BaseException {
    private static final long serialVersionUID = 1L;
 
    public UnrecognizedUserException() {
        super(-1, "无法识别用户身份");
    }
}
