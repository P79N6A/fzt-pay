package cn.yesway.bmw.manage.controller;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.yesway.bmw.manage.entity.MgtRole;
import cn.yesway.bmw.manage.entity.MgtUser;
import cn.yesway.bmw.manage.entity.MgtUserRole;
import cn.yesway.bmw.manage.entity.Pager;
import cn.yesway.bmw.manage.service.MgtRoleService;
import cn.yesway.bmw.manage.service.MgtUserRoleService;
import cn.yesway.bmw.manage.service.MgtUserService;

@Controller
@RequestMapping("/user/*")
public class UserController {
	@Autowired
	private MgtUserService userService;
	@Autowired
	private MgtRoleService roleService;
	@Autowired
	private MgtUserRoleService mgtUserRoleService;

	/**
	 * 列表
	 * @param user
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("userList.html")
	public String userList(MgtUser user, ModelMap model, Pager page) {
		Pager pager = userService.findPageList(user, page.getPageNumber(),page.getPageSize());
		model.put("pager", pager);
		return "user/userList";
	}

	/**
	 * 跳转到分配角色页面
	 * @param userId
	 * @param loginName
	 * @param model
	 * @return
	 */
	@RequestMapping("setroles.html")
	public String setRoles(@RequestParam String userId,@Param("group") String group, @RequestParam String loginName, ModelMap model) {

		MgtRole mgtRole = new MgtRole();
		List<MgtRole> checkRoleList = userService.getRolesByUserId(userId);
		List<MgtRole> allRoleList = roleService.findList(mgtRole);

		model.put("checkRoleList", checkRoleList);
		model.put("allRoleList", allRoleList);
		model.put("userId", userId);
		model.put("group", group);
		model.put("loginName", loginName);
		return "user/setRoles";
	}

	/**
	 * 保存角色
	 * @param userId
	 * @param roleList
	 * @param group
	 * @param model
	 * @param redirect
	 * @return
	 */
	@RequestMapping("saveRole")
	@ResponseBody
	public String saveRole(String userId, String roleList,String group, ModelMap model, final RedirectAttributes redirect){
		String message = "操作成功";
		if(StringUtils.isBlank(userId)){
			message = "操作失败";
		}else{
			String[] role = {};
			role = roleList.split(",");
			mgtUserRoleService.deleteByUserId(userId);
			for(int i=0; i<role.length;i++){
				MgtUserRole userRole = new MgtUserRole();
				userRole.setRoleId(role[i]);
				userRole.setUserId(userId);
				mgtUserRoleService.save(userRole);
			}
		}
		
		return message;
	}
	
	
	/**
	 * 跳转到重置密码页面
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("resetPassword.html")
	public String resetPassword(@RequestParam String userId, ModelMap model) { if (userId != null && !"".equals(userId)) {
			MgtUser mgtUser = userService.getById(userId);
			model.put("mgtUser", mgtUser);
		}
		return "user/resetPassword";
	}
	
	/**
	 * 跳转到新增/修改页面
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("addorUpdate")
	public String addorUpdateUser(@RequestParam(required = false) String userId,
			ModelMap model) {
		if (StringUtils.isNotBlank(userId)) {
			MgtUser user = userService.getById(userId);
			model.put("mgtUser", user);
			model.put("userId", userId);
		}
		return "user/userAddorUpdate";
	}

	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	@RequestMapping("deleteMgtUser")
	@ResponseBody
	public String deleteMgtUser(@RequestParam String userId) {
		int result = 0;// 删除数据条数 0失败、1成功
		if (StringUtils.isNotBlank(userId)) {
			result = userService.delete(userId);
		}
		String msg = new Integer(result).toString();
		return msg;
	}

	/**
	 * 保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("save")
	public String save(MgtUser user, RedirectAttributes model) {
		int result = 0;
		if (user != null && StringUtils.isNotBlank(user.getUserId())) {
			result = userService.update(user);
		} else {
			user.setUserId(UUID.randomUUID().toString());
			user.setAddTime(new Date());
			user.setPassword(new Sha1Hash(user.getPassword()).toString());
			result = userService.save(user);
		}
		String msg = result == 1 ? "操作成功" : "操作失败";
		model.addFlashAttribute("message", msg);
		return "redirect:/user/userList.html";
	}

	/**
	 * 重置密码
	 * @param user
	 * @return
	 */
	@RequestMapping("resetPassword")
	@ResponseBody
	public String resetPassword(MgtUser user) {
		int result = 0;
		if (StringUtils.isNotBlank(user.getUserId())
				&& StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(new Sha1Hash(user.getPassword()).toString());
			result = userService.update(user);
		}
		String msg = result == 1 ? "操作成功" : "操作失败";
		return msg;
	}
}
