package cn.yesway.bmw.manage.controller;

import cn.yesway.bmw.manage.utils.TokenGeneratorUtil;
import com.alibaba.fastjson.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TokenGetController {
	
	@RequestMapping(value="/getToken",method=RequestMethod.GET)
	@ResponseBody
	public String getToken(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		String fileName = request.getParameter("name");
		String fileSize = request.getParameter("size");
		String parentPath = request.getParameter("parentPath");
		
		JSONObject json = new JSONObject();
		try {
			String token = TokenGeneratorUtil.generateToken(fileName, fileSize, parentPath);
			if(StringUtils.isNotEmpty(token)){
				json.put("token", token);
				json.put("success", true);
				json.put("message", "OK");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	} 
}
