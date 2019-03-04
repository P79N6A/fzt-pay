package cn.yesway.pay.center.exception;


public class InvalidTokenException extends BaseException {
    private static final long serialVersionUID = 1L;

    public InvalidTokenException() {
        super(-2, "token失效或错误");
    }
}
