package cn.yesway.pay.center.exception.handle;

import cn.yesway.pay.center.exception.BaseException;
import cn.yesway.pay.center.vo.MessageHeader;


public class BaseExceptionHandler extends ExceptionHandler<MessageHeader> {

	@Override
	public MessageHeader process(Exception e) {
		return new MessageHeader(BaseException.ErrCode.INTERNAL_ERROR,"未知错误");
	}

  
}
