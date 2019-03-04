package cn.yesway.bmw.manage.controller;

import cn.yesway.bmw.manage.dto.Range;
import cn.yesway.bmw.manage.utils.FileUtil;
import cn.yesway.bmw.manage.utils.PropUtils;
import com.alibaba.fastjson.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class Html5UploadController {
	
	@RequestMapping(value="html5Upload",method=RequestMethod.GET)
	@ResponseBody
	public String getFileStart(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		
		final String token = request.getParameter("token");
		long start =0;
		JSONObject json = new JSONObject();
		File tokenedFile=null;
		try {
			tokenedFile = FileUtil.getFile(token);
			start = tokenedFile.length();
			json.put("start", start);
			json.put("success", true);
			json.put("message", "OK");
		} catch (IOException e) {
			json.put("success", false);
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	@RequestMapping(value="html5Upload",method=RequestMethod.POST)
	@ResponseBody
	public String writerFile(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		
		final String token = request.getParameter("token");
		String fileName = request.getParameter("name");
		
		JSONObject json = new JSONObject();
		Range rang = null;
		long start = 0;
		File tokenedFile = null;
		InputStream inputStream =null;
		OutputStream outputStream = null;
		try {
			rang = FileUtil.parseRange(request);
			tokenedFile = FileUtil.getFile(token);
			if(tokenedFile.length() != rang.getFrom())throw new IOException("开始上传的起始位置不对");
			inputStream = request.getInputStream();
			outputStream = new FileOutputStream(tokenedFile,true);
			int read = 0;
			final byte[] bytes = new byte[1024*1024*10];
			while ((read = inputStream.read(bytes)) != -1)outputStream.write(bytes, 0, read);
			start = tokenedFile.length();
			json.put("start", start);
			json.put("success", true);
			json.put("message", "OK");
		} catch (FileNotFoundException e) {
			json.put("start", start);
			json.put("success", false);
			json.put("message", e.getMessage());
			e.printStackTrace();
		} finally{
			FileUtil.close(outputStream);
			FileUtil.close(inputStream);
			if (rang.getSize() == start) {
				fileName = token.substring(0, token.lastIndexOf("/"))+"/"+FileUtil.generateFileName()+fileName.substring(fileName.lastIndexOf("."));
				File dst = FileUtil.getFile(fileName);
				dst.delete();
				tokenedFile.renameTo(dst);
				System.out.println("TK:" + token + ", NE:" + fileName + "");
				String replace = PropUtils.get("file_replace_url");
				String file_url = PropUtils.get("file_url");
				String message = fileName.replace(replace, "");
				String imgUrl = file_url+message;
				System.out.println("imgUrl:【"+file_url+message+"】");
				json.put("message",message );
				//调用支付宝直付通上传图片
				String imageId = "orderId";//AlipayClientUtil.alizftupload(imgUrl);
				System.out.println("imageId"+imageId);
				json.put("imageId",imageId );
			}
		}
		return json.toString();
	}


	 
}
