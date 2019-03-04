package cn.yesway.pay.center.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *  用户管理ghk
 *  2017年2月6日下午2:15:28
 *  UserController
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	private final Logger logger = Logger.getLogger(UserController.class);
	
	@RequestMapping("/index")
	public String index(){
		logger.info("测试数据:..");
		return "index";
	}
}
