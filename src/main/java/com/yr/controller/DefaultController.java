package com.yr.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {
	
	/**
	 * 登录页面. 
	 * 在 springmvc 配置中配置了
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 下午7:02:46
	 */
	@RequestMapping("/loginPage")
    public String login(@RequestParam(value = "error", required = false) String error,Map<String, Object> map) {
		if (error != null) {
			map.put("error", "登录失败!!!");
            return "login";
        } 
		return "login";
    }
	
	/**
	 * 用户页面显示
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 上午9:31:23
	 */
	@RequestMapping(value="/user/a",method=RequestMethod.POST)
    public String userPage() {
		return "userDisplayPage";
    }
	
	/**
	 * 用户页面显示
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 上午9:31:23
	 */
	@RequestMapping(value="/user/b",method=RequestMethod.GET)
    public String userPageGET() {
		return "userDisplayPage";
    }
	
	/**
	 * 角色页面显示
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 上午9:31:23
	 */
	@RequestMapping(value="/role/a",method=RequestMethod.GET)
    public String rolePage() {
		return "roleDisPlayPage";
    }
	
	/**
	 * 权限页面显示
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 上午9:31:23
	 */
	@RequestMapping(value="/power/a",method=RequestMethod.GET)
    public String powerPage() {
		return "powerDisplayPage";
    }
	
	/**
	 * 注册页面
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 上午10:42:58
	 */
	@RequestMapping("/addAccount")
    public String login() {
		return "addAccount";
    }
	
	/** 
	 * 此方法处理注销请求. (登出)
	 * 切换处理程序，如果 你记得我 的功能在你的应用程序是无用的(简意: 使用了记住我功能,即使退出在另一页面访问也是可以的)
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}
	
	/**
	 * 此方法返回登录用户的主体[用户名]. 
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 上午11:02:30
	 */
	public String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	/**
	 * 无权限页面
	 * @author zxy-un
	 * 
	 * @param model
	 * @return
	 * 
	 * 上午11:05:54
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}
	
}
