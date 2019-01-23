package com.yr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.entity.Page;
import com.yr.entity.User;
import com.yr.service.SecurityUserService;

/**
 * 用户控制层
 * @author zxy-un
 * 
 * 2018年8月7日 下午9:34:13
 */
@Controller
public class SecurityUserController {
	
	@Autowired
	private SecurityUserService userService;
	
	/**
	 * 增加用户
	 * @author zxy-un
	 * 
	 * @param user
	 * @param map
	 * @return
	 * 
	 * 下午9:56:19
	 */
	@Transactional
	@RequestMapping(value="/user/u",method=RequestMethod.POST)
	public String add(User user, ModelMap map) {
		try {
			Boolean boo = userService.add(user);
			if(boo){
				map.put("addlogin", "用户添加成功");
				return "login";
			}else{
				map.put("error","添加失败!");
				return "addAccount";
			}
		} catch (Exception e) {
			map.put("error","添加失败!");
			return "addAccount";
		}
	}
	
	/**
	 * 注册
	 * @author zxy-un
	 * 
	 * @param user
	 * @param map
	 * @return
	 * 
	 * 上午9:18:31
	 */
	@Transactional
	@RequestMapping(value="/user/add",method=RequestMethod.POST)
	public String addUser(User user, ModelMap map) {
		try {
			Boolean boo = userService.add(user);
			if(boo){
				map.put("addlogin", "用户添加成功");
				return "login";
			}else{
				map.put("error","添加失败!");
				return "addAccount";
			}
		} catch (Exception e) {
			map.put("error","添加失败!");
			return "addAccount";
		}
	}
	
	/**
	 * 删除指定用户
	 * @author zxy-un
	 * 
	 * @param id
	 * @param map
	 * @return
	 * 
	 * 下午9:56:29
	 */
	@RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
	public String del(@PathVariable("id") int id, ModelMap map) {
		try {
			Boolean bool = userService.delete(id);
			if (bool) {
				map.put("dlt", 2);
				return "userDisplayPage";
			}else{
				map.put("dlt", 1);
				return "userDisplayPage";
			}
		} catch (Exception e) {
			map.put("dlt", 1);
			return "userDisplayPage";
		}
		
	}
	
	/**
	 * 修改指定用户
	 * @author zxy-un
	 * 
	 * @param user
	 * @param map
	 * @return
	 * 
	 * 下午9:56:38
	 */
    @RequestMapping(value="/user/u",method=RequestMethod.PUT)
	public String upd(User user, ModelMap map) {
    	try {
    		Boolean bool = userService.update(user);
    		if (bool) {
    			map.put("upd", 2);
    			return "userDisplayPage";
    		}else{
    			map.put("upd", 1);
    			return "userDisplayPage";
    		}
		} catch (Exception e) {
			map.put("upd", 1);
			return "userDisplayPage";
		}
	}
    
    /**
     * 获取指定用户信息(回显)
     * @author zxy-un
     * 
     * @param id
     * @param map
     * @return
     * 
     * 下午9:56:45
     */
    @RequestMapping(value="/user/get",method=RequestMethod.GET)
	public @ResponseBody User get(int id, ModelMap map) {
    	User user = userService.get(id);
    	return user;
	}
	
	/**
	 * 分页查询所有用户
	 * @author zxy-un
	 * 
	 * @param response
	 * @param request
	 * @return
	 * 
	 * 下午9:57:01
	 */
	@RequestMapping(value="/user/u",method=RequestMethod.GET)
	public @ResponseBody Page<User> sel(HttpServletResponse response,HttpServletRequest request) {
		Page<User> page = new Page<User>();

		String pageNow = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		if (pageNow != null && !"".equals(pageNow)) {
			page.setPage(Integer.valueOf(pageNow));
			page.setPageSize(Integer.valueOf(pageSize));
		}
		page = userService.select(page);
		return page;
	}
	
}
