
package cn.yesway.bmw.manage.controller;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.yesway.bmw.manage.entity.MgtRole;
import cn.yesway.bmw.manage.entity.Pager;
import cn.yesway.bmw.manage.entity.PayCenterConfiguration;
import cn.yesway.bmw.manage.service.PayCenterConfigurationService;

/**
 * @author 支付方式配置
 *  2018年1月15日下午4:11:39
 *  PayToolConfigController
 *  备注：删除操作前台页面直接注释了
 */
@Controller
@RequestMapping("/payalipay")
public class PayAlipayController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private PayCenterConfigurationService payCenterConfigurationService;
	
	
	@RequestMapping("/alipay")
	public String alipayList(PayCenterConfiguration PayCenterConfiguration,Integer pageNumber , Integer pageSize,ModelMap model){
		PayCenterConfiguration.setPayToolType(1);
		Pager pager = payCenterConfigurationService.findPageList(PayCenterConfiguration, pageNumber, pageSize);
		model.put("pager", pager);
		return "paytool/alipayList";
		
	}
	

	/**
	 * 新增/编辑
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping("addorUpdate")
	public String addorUpdate(String payConfigId,ModelMap model){
		if(StringUtils.isNotBlank(payConfigId)){
			PayCenterConfiguration payCenterConfiguration = payCenterConfigurationService.getById(payConfigId);
			model.put("pay", payCenterConfiguration);
		}
		return "paytool/alipayAddorUpdate";
	}
	@RequestMapping("detailInfo.html")
	public String detailInfo(String payConfigId,ModelMap model){
		log.info("进入支付宝详情页面，payConfigId:"+payConfigId);
		PayCenterConfiguration payCenterConfiguration = payCenterConfigurationService.getById(payConfigId);
		model.put("pay", payCenterConfiguration);
		return "paytool/detailinfo";
		
	}
	@RequestMapping("save")
	public String saveDetailInfo(PayCenterConfiguration obj,RedirectAttributes model){
		int n =0;
		if(StringUtils.isBlank(obj.getPayConfigId())){
			obj.setPayConfigId(UUID.randomUUID().toString());
			obj.setPayToolType(1);
			n = payCenterConfigurationService.save(obj);
		} else {
			PayCenterConfiguration oldConfig = payCenterConfigurationService.getById(obj.getPayConfigId());
			oldConfig.setOemId(obj.getOemId());
			oldConfig.setMchId(obj.getMchId());
			oldConfig.setMchName(obj.getMchName());
			oldConfig.setAppId(obj.getAppId());
			oldConfig.setAesKey(obj.getAesKey());
			oldConfig.setnotifyClientPWD(obj.getnotifyClientPWD());
			oldConfig.setNotifyClient(obj.getNotifyClient());
			oldConfig.setNotifyServer(obj.getNotifyServer());
			oldConfig.setnotifyServerPWD(obj.getnotifyServerPWD());
			oldConfig.setThirdNotifyUrl(obj.getThirdNotifyUrl());
			oldConfig.setNotityUrl(obj.getNotityUrl());
			oldConfig.setYeswayPublicKey(obj.getYeswayPublicKey());
			oldConfig.setYeswayPrivateKey(obj.getYeswayPrivateKey());
			oldConfig.setAlipayPublicKey(obj.getAlipayPublicKey());
			oldConfig.setYeswayAlipayPrivateKey(obj.getYeswayAlipayPrivateKey());
			n = payCenterConfigurationService.update(oldConfig);
		}
		String msg =n ==1 ?"操作成功!":"操作失败!";
		model.addFlashAttribute("message", msg);
		return "redirect:/payalipay/alipay";
	}
	/**
	 *删除操作
	 * @return
	 */
	@RequestMapping("deletepay")
	@ResponseBody
	public String deleterole(String payConfigId,ModelMap model,HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirect){
		int result = 0;
		if(!StringUtils.isBlank(payConfigId)){
			result = payCenterConfigurationService.delete(payConfigId);
		}
		return result + "";
	}
	
}
