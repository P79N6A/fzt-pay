package cn.yesway.pay.center.exception;


public class RepeatOrderException extends BaseException {
    private static final long serialVersionUID = -112492189895425437L;

    public RepeatOrderException() {
        super(BaseException.ErrCode.REPEAT_ORDER_EXCEPTION, "重复提交的订单");
    }

}