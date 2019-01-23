package com.yr.filter;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

/**
 * Created by yangyibo on 17/1/19.
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    //decide 方法是判定是否拥有权限的决策方法
	//取得一个人所有权限,与我们从界面操作的连接(链接地址) 加 提交方式 Method(get,post,put..) 进行比较
	// Authentication :    Url登录成功,都存入这个里面,如果没有登录则拦截
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String url, method;
        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/*.jsp"); // /**/*.jsp 如果jsp在文件夹下面需这样过滤
        
        if (matcher.matches(request)) {
            return;
        } else {
	        for (GrantedAuthority ga : authentication.getAuthorities()) {
	            if (ga instanceof MyGrantedAuthority) { // 循环数据库 用户对应的权限 ,在 自定义验证类中对应权限(CustomUserService)
	                MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
	                url = urlGrantedAuthority.getPermissionUrl();
	                method = urlGrantedAuthority.getMethod();
	                matcher = new AntPathRequestMatcher(url); //表示我们需要跳转的页面
	                if (matcher.matches(request)) {
	                    //当权限表权限的method为All时表示拥有此路径的所有请求方式权利。
	                    if (method.equals(request.getMethod()) || "All".equals(method)) {
	                        return;
	                    }
	                }
	            } else if (ga.getAuthority().equals("ROLE_ANONYMOUS")) { // 未登录只允许访问 login 页面 (表示数据库中用户没有对应的权限)
	            	matcher = new AntPathRequestMatcher("/loginPage"); // 注意: 这里是访问登录页面所需要经过的 Mappring 路径
	                if (matcher.matches(request)) {
	                    return;
	                }
	            } 
	        }
	        // 没权限时,进入无权页面
        	matcher = new AntPathRequestMatcher("/Access_Denied"); // 注意: 这里是访问登录页面所需要经过的 Mappring 路径
            if (matcher.matches(request)) {
                return;
            }
	        throw new AccessDeniedException("no right");
        }
    }



    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
