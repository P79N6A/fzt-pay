
package cn.yesway.pay.center.exception;

public class RetunrnMissageMethodException extends BaseException{

	
	private static final long serialVersionUID = -8067782546383275648L;

	public RetunrnMissageMethodException() {
		super(BaseException.ErrCode.RETURN_MISSAGE_METHOD,"缺少必选参数");
	}

}
