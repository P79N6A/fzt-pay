package cn.yesway.pay.center.exception.handle;

import cn.yesway.pay.center.vo.MessageHeader;


public class ExceptionHandlerFactory {

    private static ExceptionHandler<MessageHeader> handler;

    private ExceptionHandlerFactory() {

    }

    /**
     * 返回一个单例的异常处理器
     *
     * @return 异常处理器
     */
    public static ExceptionHandler<MessageHeader> getHandler() {
        if (handler == null) {
            handler = new MessageHeaderExceptionHandler(new BaseExceptionHandler());
        }
        return handler;
    }
}
