package com.yr.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yr.controller.DefaultController;
import com.yr.entity.Power;
import com.yr.service.SecurityPowerService;

/**
 * 自定义标签使用(security标签失效所以需自定义表示来使用)
 * @author zxy-un
 * 
 * 2018年8月24日 下午3:21:20
 */
@Component
public class AuthorizeTag extends BodyTagSupport {

	@Autowired
	private SecurityPowerService securityPowerService;

	private static final long serialVersionUID = 1L;

	private static AuthorizeTag  serverHandler ;
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {  
        serverHandler = this;  
        serverHandler.securityPowerService = this.securityPowerService;        
        // 初使化时将已静态化的testService实例化
    }
	
	
	
	private String permissionsUrl; // 权限判断url
	private String permissionsMethod; // 请求方式

	public String getPermissionsUrl() {
		return permissionsUrl;
	}

	public void setPermissionsUrl(String permissionsUrl) {
		this.permissionsUrl = permissionsUrl;
	}

	public String getPermissionsMethod() {
		return permissionsMethod;
	}

	public void setPermissionsMethod(String permissionsMethod) {
		this.permissionsMethod = permissionsMethod;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
				.getAttribute("SPRING_SECURITY_CONTEXT");
		// 获取当前登录名
		DefaultController dc = new DefaultController();
		String name = dc.getPrincipal();
		
		List<Power> queryAll = serverHandler.securityPowerService.select();
		String date[]=permissionsMethod.split(","); // 页面上如果有多个需要判断的method,则根据 ',' 拆分
		// 如果数据库里有该链接，并且该用户的权限拥有该链接，则显示 。如果数据库没有该链接则不显示
		boolean flag = true;
		permissionsUrl = StringUtils.substringBeforeLast(permissionsUrl, "/");
		for (int i = 0;i < date.length;i++) {
			for (Power resources : queryAll) {
				if (StringUtils.substringBeforeLast(resources.getHttpUrl(), "/").equals(permissionsUrl)
						&& (resources.getUsageMethod().equals(date[i])) || resources.getUsageMethod().equals("All"))
					flag = false;
			}
		}
		if (flag) // 数据库中没有该链接，直接显示
			return EVAL_BODY_INCLUDE;
		else {
			Power resources = new Power();
			resources.setUserName(name);
			permissionsUrl = permissionsUrl + "/%";
			resources.setHttpUrl(permissionsUrl);
			if (1 == date.length) { // 如果长度等于一 ,表示只有一个方法的请求,直接显示
        	    resources.setUsageMethod(permissionsMethod);
        	    List<Power> resourcesList = serverHandler.securityPowerService.AuthorTag(resources);//loadMenu(resources);
        	    if(resourcesList.size()>0)	return EVAL_BODY_INCLUDE; // 数据库中有该链接，并且该用户拥有该角色，显示
        	} else {
        	    for (int i = 0;i < date.length;i++) { // 如果有多个method的请求,那么循环判断是否拥有其中权限,如若有便会显示
        	    	resources.setUsageMethod(date[i]); // 根据所拆分的method,获得对应的请求
        			List<Power> resourcesList = serverHandler.securityPowerService.AuthorTag(resources);
                    if(resourcesList.size()>0)  return EVAL_BODY_INCLUDE; // 数据库中有该链接，并且该用户拥有该角色，显示
        	    }
        	}
		}
		return this.SKIP_BODY; // 不显示
	}

}