package cn.yesway.pay.center.exception;

import java.io.Serializable;

public class SystemException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 3787730660315875183L;

	private String message;
	private String code;
	private String title = "系统出错了！";
	private String myOid;

	public SystemException(String title,String code, String message,String myOid) {
		super(message);
		this.message = message;
		this.code = code;
		this.myOid=myOid;
		this.title=title;
	}


	public SystemException(String message) {
		super(message);
		this.message = message;
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

	public String getMyOid() {
		return myOid;
	}

	public void setMyOid(String myOid) {
		this.myOid = myOid;
	}

}
