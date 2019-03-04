package cn.yesway.bmw.manage.controller;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yesway.bmw.manage.entity.OEM;
import cn.yesway.bmw.manage.service.OemService;

import com.google.gson.Gson;

@Controller
@RequestMapping("/oem/*")
public class OemController {
	private  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private OemService oemService;

	@RequestMapping("oemList.html")
	public String goOemListPage(@RequestParam(required=false)OEM oem ,ModelMap model){
		List<OEM> oemList = oemService.findList(null);
		model.put("oemList", oemList);
		return "oem/oemList";
	}
	
	
	@RequestMapping("addOrUpdateOem.html")
	public String goAddOemPage(@RequestParam(required=false)String oemId ,ModelMap model){
		if(StringUtils.isNotBlank(oemId)){
			OEM oem = oemService.getById(oemId);
			model.put("oem", oem);
		}
		return "oem/oemAddOrUpdate";
	}
	@RequestMapping("nameexist")
	@ResponseBody
	public boolean nameexist(@RequestParam(required=false)String oemName ,ModelMap model){
		OEM oem = new OEM();
		oem.setOemName(oemName);
		return oemService.findList(oem).isEmpty();
	}
	@RequestMapping("idexist")
	@ResponseBody
	public boolean idexist(@RequestParam(required=false)String oemId ){
		return oemService.getById(oemId)==null;
	}
	@RequestMapping("save")
	@ResponseBody
	public String save(@RequestBody(required=true) OEM oem ,ModelMap model){
		String resultStr = "保存失败";
		if(StringUtils.isBlank(oem.getOemId())){
			log.error("OemController类 - save方法 - oem.getOemId()为空 ，oem="+new Gson().toJson(oem));
		}
		OEM oem1 = oemService.getById(oem.getOemId());
		int result = 0;
		if(oem1!=null){
			try {
				result = oemService.update(oem);
			} catch (InvalidParameterException e) {
				log.error("OemController类 - save方法 - 修改oem时参数异常 ，oem="+new Gson().toJson(oem), e);
			}
		}else{
			
			result = oemService.save(oem);
			
		}
		if(result>0) resultStr = "保存成功";
		return resultStr;
	}
	
	@RequestMapping("deleteOem")
	@ResponseBody
	public boolean deleteOem(@RequestParam(required=true)String oemId){
		int result = oemService.delete(oemId);
		return result>0;
	}
}
