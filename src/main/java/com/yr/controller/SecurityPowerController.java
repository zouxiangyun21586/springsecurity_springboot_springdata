package com.yr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.entity.Page;
import com.yr.entity.Power;
import com.yr.service.SecurityPowerService;
import com.yr.util.JsonUtils;

/**
 * 权限控制层
 * @author zxy-un
 * 
 * 2018年8月7日 下午9:33:45
 */
@Controller
public class SecurityPowerController {

	@Autowired
	private SecurityPowerService powerService;
	
	/**
	 * 增加权限
	 * @author zxy-un
	 * 
	 * @param Power
	 * @param map
	 * @return
	 * 
	 * 下午9:27:04
	 */
	@Transactional
	@RequestMapping(value = "/power/p", method = RequestMethod.POST)
	public String add(Power power, ModelMap map) {
		Boolean boo = powerService.add(power);
		if (boo) {
			map.put("addSu", 2);
			return "powerDisplayPage";
		} else {
			map.put("addSu", 1);
			return "powerDisplayPage";
		}
	}
	
	/**
	 * 删除指定权限
	 * @author zxy-un
	 * 
	 * @param Power
	 * @param map
	 * @return
	 * 
	 * 下午9:28:10
	 */
	@Transactional
	@RequestMapping(value = "/power/{id}", method = RequestMethod.DELETE)
	public String del(Power power, ModelMap map) {
		Boolean bool = powerService.delete(power);
		if (bool) {
			map.put("dlt", 2);
			return "powerDisplayPage";
		} else {
			map.put("dlt", 1);
			return "powerDisplayPage";
		}
	}
	
	/**
	 * 修改指定权限
	 * @author zxy-un
	 *   
	 * @param power
	 * @param map
	 * @return
	 * 
	 * 下午9:28:17
	 */
	@Transactional
	@RequestMapping(value = "/power/p", method = RequestMethod.PUT)
	public String upd(Power power, ModelMap map) {
		Boolean bool = powerService.update(power);
		if (bool) {
			map.put("upd", 2);
			return "powerDisplayPage";
		} else {
			map.put("upd", 1);
			return "powerDisplayPage";
		}
	}
	
	/**
	 * 获取指定权限的所有信息(数据回显)
	 * @author zxy-un
	 * 
	 * @param id
	 * @param map
	 * @return
	 * 
	 * 下午9:28:30
	 */
	@RequestMapping(value = "/power/getPower", method = RequestMethod.GET)
	public @ResponseBody Power get(int id, ModelMap map) {
		Power power = powerService.get(id);
		return power;
	}
	
	/**
	 * 分页查询所有权限
	 * @author zxy-un
	 * 
	 * @param response
	 * @param request
	 * @return
	 * 
	 * 下午9:28:47
	 */
	@RequestMapping(value = "/power/p", method = RequestMethod.GET)
	public @ResponseBody Page<Power> sel(HttpServletResponse response, HttpServletRequest request) {
		Page<Power> page = new Page<Power>();

		String pageNow = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		if (pageNow != null && !"".equals(pageNow)) {
			page.setPage(Integer.valueOf(pageNow));
			page.setPageSize(Integer.valueOf(pageSize));
		}
		page = powerService.query(page);
		return page;
	}
	
	/**
	 * 查询指定角色所拥有的权限(回显)
	 * @author zxy-un
	 * 
	 * @param id
	 * @return
	 * 
	 * 下午9:41:09
	 */
	@RequestMapping(value = "/power/rolePower", method = RequestMethod.GET)
	public @ResponseBody List<Power> rolePowerAll(int id) {
		List<Power> listPower = powerService.rolePower(id);
		return listPower;
	}
	
	/**
	 * 查询所有权限(用于角色页面显示所有权限
	 * @author zxy-un
	 * 
	 * @return
	 * 
	 * 上午8:40:10
	 */
	@RequestMapping(value = "/power/powerAll", method = RequestMethod.GET)
	public @ResponseBody List<Power> powerAll() {
		List<Power> listPower = powerService.select();
		return listPower;
	}

	/**
	 * 给指定角色赋权(根据指定角色id获取页面所选中的权限)
	 * @author zxy-un
	 * 
	 * @param ids
	 * @param request
	 * @param map
	 * @return
	 * 
	 * 下午9:41:45
	 */
	@RequestMapping(value = "/power/powerGivePower", method = RequestMethod.GET)
	public String roleGivePower(int ids, HttpServletRequest request, ModelMap map) {
		String powerIds[] = request.getParameterValues("checkname");
		Boolean boo = powerService.givePower(powerIds, ids);
		if (boo) {
			map.put("rgp", 2);
			return "roleDisPlayPage";
		} else {
			map.put("rgp", 1);
			return "roleDisPlayPage";
		}
	}
}
