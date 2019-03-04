package cn.yesway.bmw.manage.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.yesway.bmw.manage.common.Constans;
import cn.yesway.bmw.manage.entity.MgtMenu;
import cn.yesway.bmw.manage.entity.MgtRoleMenu;
import cn.yesway.bmw.manage.entity.MgtUser;
import cn.yesway.bmw.manage.entity.MgtUserRole;
import cn.yesway.bmw.manage.entity.UserProfile;
import cn.yesway.bmw.manage.service.MgtMenuService;
import cn.yesway.bmw.manage.service.MgtRoleMenuService;
import cn.yesway.bmw.manage.service.MgtUserRoleService;
import cn.yesway.bmw.manage.service.MgtUserService;

@Controller
public class LoginController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private	MgtUserService mgtUserService;
	@Autowired
	private MgtUserRoleService mgtUserRoleService;
	@Autowired
	private MgtRoleMenuService  mgtRoleMenuService;
	@Autowired
	private MgtMenuService mgtMenuService;
	
	/**
	 * 用户有关对象
	 */
	public UserProfile userProfile = new UserProfile();

	
	/*@RequestMapping("login")
	public String login(){
		log.info("\n 进入login测试方法..");
		return "index";
		
	}*/
	@RequestMapping("index.html")
	public String index(ModelMap model,HttpServletRequest req, HttpServletResponse res){
		// 得到session
		HttpSession session = req.getSession(true);
		Subject currentUser = SecurityUtils.getSubject();
		MgtUser user = (MgtUser)currentUser.getPrincipal();
		MgtUserRole mgtUserRole =new MgtUserRole();
		mgtUserRole.setUserId(user.getUserId());
		//JSONObject obje = new JSONObject();
		//1、根据用户查询用户所属角色
		//MgtUserRole obj=mgtUserRoleService.getById(user.getUserId());
		List<MgtUserRole> resultList=mgtUserRoleService.findList(mgtUserRole);
		if(resultList!=null && resultList.size()>0){
			MgtRoleMenu mgtRight = new MgtRoleMenu();
			mgtRight.setRoleId(resultList.get(0).getRoleId());
			//2、根据用户所属角色获得角色所具有的权限
		    List<MgtRoleMenu>	objList=mgtRoleMenuService.findList(mgtRight);
		    if(objList!=null && objList.size()>0){
		    	List<String> powerids = new ArrayList<String>();
		    	String[] poweridarry=objList.get(0).getMenuId().split(",");
		    	if(poweridarry !=null && poweridarry.length>0)
	    		{
	    			for(String str:poweridarry)
	    			{
	    				powerids.add(str);
	    			}
	    		}
		    	List<MgtMenu> mgtMenuList=mgtMenuService.queryLoadInfo(powerids);
		    	if(mgtMenuList==null || mgtMenuList.size()<1){
		    		log.info("根据角色查询所具有的权限失败!");
		    		userProfile.setModuleList(null);
		    	}
		    	userProfile.setModuleList(mgtMenuList);
		    	//obje.put("dataJson", mgtOperationList);
		    	/*for(String powerid:poweridarry){//保存这个角色的权限
		    		MgtOperation obj = new MgtOperation();
		    		obj.setOperationId(powerid);
		    		MgtOperation mgtOperation=mgtOperationService.getById(powerid);
		    		List<MgtOperation> mgtOperationList=mgtOperationService.findList(obj);
			     }*/
		    }else{
		    	log.info("根据用户角色查询具有的权限为Null");
		    }
		    
		}else{
			log.info("根据用户查询用户所属角色失败!");
		}
		session.setAttribute("userProfile", userProfile);
		//model.addAttribute("objList", obje);
		model.put("user", user);
		return "index";
	}
	
	@RequestMapping("login.html")
	public String loginHtml(ModelMap model){
		return "login";
	}
	
	@RequestMapping("logout")
	public String logout(ModelMap model){
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:login.html";
	}
	
	@RequestMapping("login")
	public String login(ModelMap model,@ModelAttribute("form")MgtUser user){
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), new Sha1Hash(user.getPassword()).toString());
		HttpSession session= req.getSession();
		
		Subject currentUser = SecurityUtils.getSubject();
		String errorMessage = "";
		try {
			currentUser.login(token);
			//String useName=user.getLoginName();
			//String password=user.getPassword();
			MgtUser obj=mgtUserService.getUserByLoginName(user.getLoginName());
			if(obj==null || obj.equals("")){
				log.info("\n 根据登录用户查询所属信息为空");
				return null;
			}
			session.setAttribute(Constans.CURRENT_USER_KEY, obj);//把用户保存进session
			String ip = req.getHeader("x-forwarded-for");  
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = req.getHeader("Proxy-Client-IP");  
	        }  
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = req.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = req.getRemoteAddr();
	        }  
	        if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){    
                //根据网卡取本机配置的IP    
                InetAddress inet=null;    
                try {    
                    inet = InetAddress.getLocalHost();    
                } catch (UnknownHostException e) {    
                    e.printStackTrace();    
                }    
                ip= inet.getHostAddress();    
            }    
	        
	        session.setAttribute(Constans.LOGIN_IP, ip);
	        
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			String loginTime = sdf.format(new Date());
			session.setAttribute(Constans.LOGIN_TIME, loginTime);
			return "redirect:index.html";
		} catch (UnknownAccountException e) {
			errorMessage = "用户不存在";
		} catch (IncorrectCredentialsException e) {
			errorMessage = "密码错误";
		} catch (LockedAccountException e) {
			errorMessage = "用户锁定";
		} catch (DisabledAccountException e) {
			errorMessage = "用户被禁止";
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			errorMessage = "未知错误";
		}
		model.put("errorMessage", errorMessage);
		return "login";
	}
	
	public static void main(String[] args) {
		//admin: d033e22ae348aeb5660fc2140aec35850c4da997
		System.out.println(new Sha1Hash("admin"));
	}
}
