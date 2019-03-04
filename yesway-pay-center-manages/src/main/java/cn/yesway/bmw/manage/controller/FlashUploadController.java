package cn.yesway.bmw.manage.controller;

import cn.yesway.bmw.manage.utils.FileUtil;
import com.alibaba.fastjson.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.MultipartStream.MalformedStreamException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class FlashUploadController {
	
	@RequestMapping("flashUpload")
	@ResponseBody
	public String writeFile(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		/** flash @ windows bug */
		request.setCharacterEncoding("utf8");
		JSONObject json = new JSONObject();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			json.put("success", false);
			json.put("message", "ERROR: It's not Multipart form");
			return json.toString();
		}
		
		String token = null;
		String fileName = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		ServletFileUpload upload = new ServletFileUpload();
		try {
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				inputStream = item.openStream();
				if(item.isFormField()){
					String value = Streams.asString(inputStream);
					if ("token".equals(name)) {
						token = value;
					}
					System.out.println(name + ":" + value);
				}else{
					fileName = item.getName();
					File tokenFile = FileUtil.getFile(token);
					outputStream = new FileOutputStream(tokenFile);
					int read = 0;
					final byte[] bytes = new byte[1024*1024*10];
					while ((read = inputStream.read(bytes)) != -1)outputStream.write(bytes, 0, read);
					outputStream.flush();
				}
			}
		}catch(MalformedStreamException e){
			outputStream.flush();
			json.put("success", false);
			json.put("message", e.getMessage());
		} catch (FileUploadException e) {
			json.put("success", false);
			json.put("message", e.getMessage());
			e.printStackTrace();
		}finally{
			FileUtil.close(outputStream);
			FileUtil.close(inputStream);
		}
		File tokenFile = FileUtil.getFile(token);
		String fileNamePath = token.substring(0, token.lastIndexOf("/"))+"/"+FileUtil.generateFileName()+fileName.substring(fileName.lastIndexOf("."));;
		File dst = FileUtil.getFile(fileNamePath);
		dst.delete();
		tokenFile.renameTo(dst);
		long length = FileUtil.getFile(fileNamePath).length();
		json.put("start", length);
		json.put("success", true);
		if(fileNamePath.indexOf("upload")!=-1){
			json.put("message", fileNamePath.substring(fileNamePath.indexOf("upload")+6));
		}else{
			json.put("message", fileNamePath.substring(fileNamePath.indexOf("/")));
		}
		return json.toString();
	}
}
