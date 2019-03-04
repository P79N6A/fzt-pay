package cn.yesway.pay.order.exception;


public class NotOrderTheServiceException extends BaseException {
    public NotOrderTheServiceException() {
        super(-6, "该用户未订购此服务");
    }
}
