package cn.yesway.pay.center.exception;

import java.io.Serializable;

/**
 * title 业务受理失败 code 后厂编码 message 失败原因 detail 可以是堆栈信息
 * 
 * @author liwenxian
 * 
 */
public class BusiException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 3787730660315875183L;

	private String message;
	private String code;
	private String title = "系统出错了！";
	private String detail;
	private String myOid;// 在session中用于保存受理信息的ID

	public BusiException(String message) {
		super(message);
		this.message = message;
	}

	public BusiException(String code, String message) {
		super(message);
		this.message = message;
		this.code = code;
	}

	public BusiException(String title, String code, String message) {
		super(message);
		this.message = message;
		this.code = code;
		this.title = title;
	}

	/**
	 * 建议使用此方法
	 * @param title
	 * @param code
	 * @param message
	 * @param detail
	 * @param myOid
	 */
	public BusiException(String title, String code, String message,
			String detail, String myOid) {
		super(message);
		this.message = message;
		this.code = code;
		this.title = title;
		this.detail = detail;
		this.myOid = myOid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMyOid() {
		return myOid;
	}

	public void setMyOid(String myOid) {
		this.myOid = myOid;
	}

}
