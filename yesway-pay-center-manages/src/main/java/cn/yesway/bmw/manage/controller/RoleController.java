package cn.yesway.bmw.manage.controller;

import java.util.List;

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

import cn.yesway.bmw.manage.entity.MgtMenu;
import cn.yesway.bmw.manage.entity.MgtRole;
import cn.yesway.bmw.manage.entity.MgtRoleMenu;
import cn.yesway.bmw.manage.entity.Pager;
import cn.yesway.bmw.manage.service.MgtMenuService;
import cn.yesway.bmw.manage.service.MgtRoleMenuService;
import cn.yesway.bmw.manage.service.MgtRoleService;


@Controller
@RequestMapping("/role/*")
public class RoleController {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private MgtRoleService roleService;
	@Autowired
	private MgtMenuService mgtMenuService;
	@Autowired
	private MgtRoleMenuService  mgtRoleMenuService;
	
	/**
	 * 列表页
	 * @param role
	 * @param pageNumber
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping("roleList.html")
	public String roleList(MgtRole role,Integer pageNumber , Integer pageSize,ModelMap model){
		Pager pager = roleService.findPageList(role, pageNumber, pageSize);
		model.put("pager", pager);
		return "role/roleList";
	}
	
	/**
	 * 新增/编辑
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping("addorUpdate")
	public String addorUpdate(String roleId,ModelMap model){
		if(StringUtils.isNotBlank(roleId)){
			MgtRole role = roleService.getById(roleId);
			model.put("role", role);
		}
		return "role/mgtRoleAddorUpdate";
	}
	
	/**
	 * 验证角色名称是否可用
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("nametexist")
	@ResponseBody
	public Boolean nametexist(String roleName,String roleId, ModelMap model){
		if(StringUtils.isNotBlank(roleName)){
			MgtRole role = roleService.getByName(roleName);
			if(role==null){
				return true;
			}else if (role!=null && roleId.equals(role.getRoleId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 验证编号是否可用
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("idexist")
	@ResponseBody
	public Boolean idexist(String roleName, String roleId, ModelMap model){
		if(StringUtils.isNotBlank(roleId)){
			MgtRole role = roleService.getById(roleId);
			if(role==null){
				return true;
			}else if (role!=null && roleName !=null && !"".equals(roleName) && roleName.equals(role.getRoleName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 保存角色
	 * @param role
	 * @param model
	 * @param req
	 * @param res
	 * @param redirect
	 * @return
	 */
	@RequestMapping("save")
	public String saveRole(MgtRole role,ModelMap model,HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirect){
		String message = "操作成功";
		try{
			MgtRole role1 = roleService.getById(role.getRoleId());
			
		if(role1==null){
			roleService.save(role);
		}else{
			roleService.update(role);
		}}catch(Exception e){
			e.printStackTrace();
			message="操作失败";
		}
		model.put("message", message);
		return "redirect:/role/roleList.html";
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping("deleterole")
	@ResponseBody
	public String deleterole(String roleId,ModelMap model,HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirect){
		int result = 0;
		if(!StringUtils.isBlank(roleId)){
			result = roleService.delete(roleId);
		}
		return result + "";
	}
	
	/**
	 * 授权
	 * @param roleId
	 * @param model
	 * @param req
	 * @param res
	 * @param redirect
	 * @return
	 */
	@RequestMapping("authorizerole.html")
	public String authorizerole(String roleId,ModelMap model,HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirect){
		log.info("进入角色授权页面:roleId"+roleId);
		MgtMenu obj = new MgtMenu();
		StringBuffer json = new StringBuffer("[") ;
		String data = "" ;
		obj.setStatus(0);
		List<MgtMenu> result=mgtMenuService.findList(obj);
		for(int i=0;i<result.size();i++){
			json.append("{TREEID:\"" + result.get(i).getMenuId() + "\",") ;
			json.append("TREEPID:\"" + result.get(i).getParentId() + "\",") ;
			json.append("TREENAME:\"" + result.get(i).getMenuName() + "\",") ;
			data = json.substring(0,json.lastIndexOf(",")) + "}," ;
			json = new StringBuffer(data) ;
		}
		data = json.substring(0,json.length()-1) + "]" ;
		log.info("data数据："+data);
		//model.put("mgtOperationList", result);
		model.put("roleId", roleId);
		model.put("functionTree", data);
		return "role/authorize";
		
	}
	
	/**
	 * 保存角色授权信息
	 * @param powerids
	 */
	@RequestMapping("saveAuthorize.html")
	@ResponseBody
	public String saveAuthorize(MgtRoleMenu mgtRoleMenu,String powerids){
		log.info("进入角色授权信息功能"+powerids);
		//String[] poweridarry=powerids.split(",");
		/*for(String powerid:poweridarry){//保存这个角色的权限
		obj.setRightId(powerid);
	     }*/
		int length;
		MgtRoleMenu obj = new MgtRoleMenu();
		obj.setRoleId(mgtRoleMenu.getRoleId());
		List<MgtRoleMenu> result=mgtRoleMenuService.findList(obj);
		if(result !=null && result.size()>0){
			log.info("该角色授权信息已经存在,执行修改操作");
			obj.setMenuId(powerids);
			length=mgtRoleMenuService.update(obj);
			if(length>0){
				log.info(obj.getRoleId()+"角色授权信息修改成功!");
			}else{
				log.info(obj.getRoleId()+"角色授权信息修改失败!");
			}
		}else{
			obj.setMenuId(powerids);
		    length=mgtRoleMenuService.save(obj);
		if(length>0){
			log.info("角色权限信息保存成功!");
		}else{
			log.info("角色权限信息保存失败");
		}
		}
		return length+"";
	}
}
