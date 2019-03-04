package cn.yesway.pay.order.exception;


public class StoppedUserException extends BaseException {
    public StoppedUserException() {
        super(-5, "该用户已停用");
    }
}
