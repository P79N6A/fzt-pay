package cn.yesway.pay.center.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.yesway.pay.center.exception.handle.ExceptionHandlerFactory;

import com.alibaba.fastjson.JSON;

public class ResponseMessageHearder {
	private final static Logger logger = Logger.getLogger(ResponseMessageHearder.class);
	
	public static void responseMessageHearderError(HttpServletResponse response,Exception e)
			throws IOException {
		response.setContentType("application/json;charset=UTF-8");
    	Map<String, Object> map = new HashMap<String, Object>();
//		map.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(e)));
		map.put("messageheader",ExceptionHandlerFactory.getHandler().process(e));
		String responseStr = JSON.toJSONString(map);           
		writeJsonReposne(response, responseStr);
		logger.error("RESPONSE_CONTENT: " + responseStr);
	}
	public static void writeJsonReposne(HttpServletResponse response,String responseString) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(responseString);
        out.flush();
        out.close();
    }
}
