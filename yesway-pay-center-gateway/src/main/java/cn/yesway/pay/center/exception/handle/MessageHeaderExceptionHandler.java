package cn.yesway.pay.center.exception.handle;

import cn.yesway.pay.center.exception.BaseException;
import cn.yesway.pay.center.vo.MessageHeader;

public class MessageHeaderExceptionHandler extends ExceptionHandler<MessageHeader> {

    protected MessageHeaderExceptionHandler(ExceptionHandler<MessageHeader> handler) {
        setNextnode(handler);
    }

    @Override
    public MessageHeader process(Exception e) {
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            return new MessageHeader(baseException.getResultCode(),baseException.getMessage());
        } else {
            return this.getNextnode().process(e);
        }
    }
}
