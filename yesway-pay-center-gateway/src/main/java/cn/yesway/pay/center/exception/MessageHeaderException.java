package cn.yesway.pay.center.exception;


public class MessageHeaderException extends BaseException {
    private static final long serialVersionUID = 1L;

    public MessageHeaderException() {
    	super(BaseException.ErrCode.MESSAGEHEADER_EXCEPTION, "消息头数据异常");
    }
}
