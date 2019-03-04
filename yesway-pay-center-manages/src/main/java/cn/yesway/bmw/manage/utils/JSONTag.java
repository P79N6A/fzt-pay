package cn.yesway.bmw.manage.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

public class JSONTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object jsonString;
	private String key;
	
	@Override
	public int doStartTag() throws JspException {
		if(jsonString == null||StringUtils.isBlank(key)){
			return SKIP_BODY;
		}
		JspWriter out = this.pageContext.getOut();
		
		JSONObject jobj = JSONObject.fromObject(jsonString);
		Object obj = (jobj == null || jobj.isNullObject()) ? "" : jobj.get(key);
		String str = "";
		if(obj instanceof Number){
			str = String.valueOf(obj);
		}else if(obj instanceof String){
			str = (String)obj;
		}
		/* 从内容体中使用消息 */
		try {
//			System.out.println("JSON>" + jobj.toString());
//			System.out.println("TAG >" + key + ": " + value);
			str = StringUtils.isBlank(str) ? "" : str;
			out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	@Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
	@Override

    public void release() {
        super.release();
        this.jsonString = null;
    }
	public Object getJsonString() {
		return jsonString;
	}
	public void setJsonString(Object jsonString) {
		this.jsonString = jsonString;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
