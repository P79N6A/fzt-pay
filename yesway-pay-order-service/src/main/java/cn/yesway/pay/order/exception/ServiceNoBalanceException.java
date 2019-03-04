package cn.yesway.pay.order.exception;


public class ServiceNoBalanceException extends BaseException {
    public ServiceNoBalanceException() {
        super(-8, "服务次数用完");
    }
}
