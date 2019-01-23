package com.yr.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yr.dao.SecurityPowerDao;
import com.yr.dao.SecurityRoleDao;
import com.yr.dao.SecurityUserDao;
import com.yr.entity.Power;
import com.yr.entity.Role;

/**
 * 验证用户名是否存在,密码是否正确
 * @author zxy-un
 * 
 * 2018年8月21日 下午8:06:07
 */
@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    SecurityUserDao userDao;
    
    @Autowired
    SecurityRoleDao roleDao;
    
    @Autowired
    SecurityPowerDao powerDao;

    /**
     * 判断用户是否存在,拥有什么角色与权限
     */
    public UserDetails loadUserByUsername(String account) {
    	com.yr.entity.User user = userDao.loadUserByAccount(account);
        if (user != null) {
            List<Role> roles = roleDao.findByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Role role : roles) {
                if (role != null && role.getRoleName() != null) {
                	List<Power> powers = powerDao.findByRoleId(role.getId());
                	for (Power power : powers) {
                		//							controller中mapping中的的访问路径(value="/user"),mapping中的访问方法(method="GET,POST..")
                		GrantedAuthority grantedAuthority = new MyGrantedAuthority(power.getHttpUrl(), power.getUsageMethod());
                		grantedAuthorities.add(grantedAuthority);
					}
                }
            }
            return new User(user.getAccount(), user.getPassWord(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + account + " do not exist!");
        }
    }

}
