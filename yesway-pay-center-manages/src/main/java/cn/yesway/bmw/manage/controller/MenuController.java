
package cn.yesway.bmw.manage.controller;

//import java.sql.Date;
import java.sql.Timestamp;

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
import cn.yesway.bmw.manage.entity.Pager;
import cn.yesway.bmw.manage.service.MgtMenuService;

/*
 * 菜单管理
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private MgtMenuService mgtMenuService;
	
	@RequestMapping("menuList.html")
	public String menuList(MgtMenu menu,Integer pageNumber , Integer pageSize,ModelMap model){
		log.info("进入查询菜单...");
		menu.setType(1);//目前只查询一级菜单
		Pager pager = mgtMenuService.findPageList(menu, pageNumber, pageSize);
		model.put("pager", pager);
		return "menu/menuList";
	}
	
	/**
	 * 新增/编辑(弹出新增框)
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping("addorUpdate")
	public String addorUpdateMenu(String menuId,ModelMap model){
		if(StringUtils.isNotBlank(menuId)){
			MgtMenu menu = mgtMenuService.getById(menuId);
			model.put("menu", menu);
		}
		return "menu/menuAddorUpdate";
	}
	
	/**
	 * 保存一级菜单
	 * @param role
	 * @param model
	 * @param req
	 * @param res
	 * @param redirect
	 * @return
	 */
	@RequestMapping("saveMenu")
	public String saveMenu(MgtMenu menu,ModelMap model,HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirect){
		log.info("进入新增菜单操作..");
		String message = "操作成功";
		try{
			MgtMenu menuInfo = mgtMenuService.getById(menu.getMenuId()+"");
			
		if(menuInfo==null){
			//menu.setMenuId(UUID.randomUUID().toString());
			menu.setMenuId(getMenuId());
			menu.setType(1);//一级菜单
			menu.setParentId("0");//默认父节点为0
			menu.setSubMenu(0);//默认一级菜单都是无子菜单的
			menu.setCreateTime(new Timestamp(System.currentTimeMillis()));
			mgtMenuService.save(menu);
		}else{
			mgtMenuService.update(menu);
		}}catch(Exception e){
			e.printStackTrace();
			message="操作失败";
		}
		model.put("message", message);
		return "redirect:/menu/menuList.html";
	}
	
	/**
	 * 删除一级菜单
	 * @return
	 */
	@RequestMapping("deletemenu")
	@ResponseBody
	public String deletemenu(String menuId,ModelMap model,HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirect){
		log.info("进入删除菜单操作..");
		int result = 0;
		if(!StringUtils.isBlank(menuId)){
			result = mgtMenuService.delete(menuId);
		}
		return result + "";
	}
	
	//进入子菜单管理
	@RequestMapping("fundsubmenu")
	public String fundsubmenu(MgtMenu menu,ModelMap model,Integer pageNumber , Integer pageSize){
		log.info("进入子菜单管理功能..");
		menu.setParentId(menu.getMenuId()+"");
		menu.setType(2);
		menu.setMenuId(null);
		Pager pager = mgtMenuService.findPageList(menu, pageNumber, pageSize);
		model.put("parentId", menu.getParentId());
		model.put("pager", pager);
		return "menu/submenu";
		
	}
	/**
	 * 子菜单新增弹出框
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping("submenuAdd")
	public String submenuAdd(String menuId,ModelMap model){
		if(StringUtils.isNotBlank(menuId)){
			MgtMenu menu = mgtMenuService.getById(menuId);
			if(menu==null || menu.equals("")){
				log.info("查询二级菜单失败");
			}
			
			model.put("menu", menu);//一级菜单
		}
		
		return "menu/submenuAdd";
	}
	/**
	 * 子菜单修改
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping("submenuUpdate")
	public String submenuUpdate(String menuId,ModelMap model,Integer pageNumber , Integer pageSize){
		if(StringUtils.isNotBlank(menuId)){
			MgtMenu menu = mgtMenuService.getById(menuId);
			if(menu==null || menu.equals("")){
				log.info("查询二级菜单失败");
			}
			MgtMenu submenu=mgtMenuService.getById(menu.getParentId());
			if(submenu==null || submenu.equals("")){
				log.info("通过子菜单ID查询父菜单失败");
			}
			model.put("menu", menu);//一级菜单
			model.put("submenu", submenu);//二级菜单
		}
		
		return "menu/submenuUpdate";
		
	}
	/**
	 * 保存二级菜单
	 * @param role
	 * @param model
	 * @param req
	 * @param res
	 * @param redirect
	 * @return
	 */
	@RequestMapping("saveSubMenu")
	public String saveSubMenu(MgtMenu menu,ModelMap model,HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirect,Integer pageNumber , Integer pageSize){
		log.info("进入新增二级菜单操作..");
		//String message = "操作成功";
		try{
			menu.setParentId(menu.getMenuId()+"");//获得父节点
			menu.setMenuId(getMenuId());
			menu.setType(2);
			menu.setCreateTime(new Timestamp(System.currentTimeMillis()));
			int m=mgtMenuService.save(menu);
			if(m<1){
				log.info("新增保存二级菜单操作失败");
			}
			MgtMenu menuUpdate = new MgtMenu();
			menuUpdate.setMenuId(Integer.parseInt(menu.getParentId()));
			menuUpdate.setSubMenu(1);//代表存在子菜单
			int n=mgtMenuService.update(menuUpdate);
			if(n<1){
				log.info("更新二级菜单操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			//message="操作失败";
		}
		
		MgtMenu menuObj = new MgtMenu();
		menuObj.setParentId(menu.getParentId());
		menuObj.setType(2);
		Pager pager = mgtMenuService.findPageList(menuObj, pageNumber, pageSize);
		model.put("pager", pager);
		
		return "menu/submenu";
	}
	/**
	 * 修改二级菜单
	 * @param role
	 * @param model
	 * @param req
	 * @param res
	 * @param redirect
	 * @return
	 */
	@RequestMapping("updateSubMenu")
	public String updateSubMenu(MgtMenu menu,ModelMap model,HttpServletRequest req, HttpServletResponse res,final RedirectAttributes redirect,Integer pageNumber , Integer pageSize){
		log.info("进入新增二级菜单操作..");
		String message = "操作成功";
		try{
			if(menu.getMenuId()!=null){
				MgtMenu menuObj =mgtMenuService.getById(menu.getMenuId()+"");
				if(menuObj==null){
					log.info("菜单为null:菜单订单号:"+menuObj.getMenuId());
				}
				menu.setParentId(menuObj.getParentId());
			}
			mgtMenuService.update(menu);
		}catch(Exception e){
			e.printStackTrace();
			message="操作失败";
		}
		model.put("message", message);
		MgtMenu menuObj = new MgtMenu();
		menuObj.setParentId(menu.getParentId());
		menuObj.setType(2);
		Pager pager = mgtMenuService.findPageList(menuObj, pageNumber, pageSize);
		model.put("pager", pager);
		
		return "menu/submenu";
		
	}
	
	 /**
	  * 随机生成菜单id
	 * @return
	 */
	public  int getMenuId(){
		    int menuIdValue = 0;
		    int menuIdvalue = mgtMenuService.getMaxValuefromMenu();
		    menuIdValue=menuIdvalue+1;
		    //int dateStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
		    //int randomStr=(int)(Math.random()*900)+100;
		    //String serial =dateStr+randomStr;
		    return menuIdValue;
		  }
}
