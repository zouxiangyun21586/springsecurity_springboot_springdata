package com.yr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.service.SecurityRoleService;

/**
 * 角色控制层
 * @author zxy-un
 * 
 * 2018年8月7日 下午9:34:04
 */
@Controller
public class SecurityRoleController {
	
	@Autowired
	private SecurityRoleService roleService;
	
	/**
	 * 添加角色
	 * @author zxy-un
	 * 
	 * @param role
	 * @param map
	 * @return
	 * 
	 * 下午9:37:20
	 */
	@Transactional
	@RequestMapping(value="/role/r",method=RequestMethod.POST)
	public String add(Role role, ModelMap map) {
		try {
			Boolean boo = roleService.add(role);
			if(boo){
				map.put("add",2);
				return "roleDisPlayPage";
			}else{
				map.put("add",1);
				return "roleDisPlayPage";
			}
		} catch (Exception e) {
			map.put("add",1);
			return "roleDisPlayPage";
		}
	}
	
	/**
	 * 删除指定角色
	 * @author zxy-un
	 * 
	 * @param role
	 * @param map
	 * @return
	 * 
	 * 下午9:37:34
	 */
	@Transactional
	@RequestMapping(value="/role/{id}",method=RequestMethod.DELETE)
	public String del(Role role, ModelMap map) {
		try {
			Boolean bool = roleService.delete(role);
			if (bool) {
				map.put("dlt", 2);
				return "roleDisPlayPage";
			}else{
				map.put("dlt", 1);
				return "roleDisPlayPage";
			}
		} catch (Exception e) {
			map.put("dlt", 1);
			return "roleDisPlayPage";
		}
	}
	
	/**
	 * 修改指定角色
	 * @author zxy-un
	 * 
	 * @param role
	 * @param map
	 * @return
	 * 
	 * 下午9:37:41
	 */
	@Transactional
    @RequestMapping(value="/role/r",method=RequestMethod.PUT)
	public String upd(Role role, ModelMap map) {
		try {
			Boolean bool = roleService.update(role);
			if (bool) {
				map.put("upd", 2);
				return "roleDisPlayPage";
			}else{
				map.put("upd", 1);
				return "roleDisPlayPage";
			}
		} catch (Exception e) {
			map.put("upd", 1);
			return "roleDisPlayPage";
		}
	}
    
	/**
	 * 获取指定角色信息(修改回显)
	 * @author zxy-un
	 * 
	 * @param id
	 * @param map
	 * @return
	 * 
	 * 下午9:37:47
	 */
    @ResponseBody
    @RequestMapping(value="/role/getRole",method=RequestMethod.GET)
	public Role get(int id, ModelMap map) {
    	Role role = roleService.get(id);
    	return role;
	}
	
    /**
     * 某用户所拥有的角色(赋权回显)
     * @author zxy-un
     * 
     * @param id
     * @param map
     * @return
     * 
     * 下午9:38:11
     */
    @RequestMapping(value="/role/userRole",method=RequestMethod.GET)
	public @ResponseBody List<Role> userRole(int id, ModelMap map) {
    	List<Role> listRole = roleService.userRole(id);
    	return listRole;
    }
    
	/**
	 * 分页查询所有角色
	 * @author zxy-un
	 * 
	 * @param response
	 * @param request
	 * @return
	 * 
	 * 下午9:38:42
	 */
	@RequestMapping(value="/role/r",method=RequestMethod.GET)
	public @ResponseBody Page<Role> sel(HttpServletResponse response,HttpServletRequest request) {
		Page<Role> page = new Page<Role>();

		String pageNow = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		if (pageNow != null && !"".equals(pageNow)) {
			page.setPage(Integer.valueOf(pageNow));
			page.setPageSize(Integer.valueOf(pageSize));
		}
		page = roleService.query(page);
		return page;
	}
	
	/**
	 * 查询所有角色(用于用户页面显示所有角色
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 下午10:31:45
	 */
	@RequestMapping(value="/role/roleAll",method=RequestMethod.GET)
	public @ResponseBody List<Role> select() {
		return roleService.select();
	}
	
	/**
	 * 根据指定用户id获取页面所选中的角色
	 * @author zxy-un
	 * 
	 * @param ids
	 * @param request
	 * @param map
	 * @return
	 * 
	 * 下午9:38:57
	 */
	@RequestMapping(value="/role/roleGivePower",method=RequestMethod.GET)
	public String roleGivePower(int ids ,HttpServletRequest request ,ModelMap map) {
		String resourceIds[] = request.getParameterValues("checkname");
		Boolean boo = roleService.givePower(resourceIds, ids);
		if (boo) {
			map.put("rgp", 2);
			return "userDisplayPage";
		}else{
			map.put("rgp", 1);
			return "userDisplayPage";
		}
	}
	
}
