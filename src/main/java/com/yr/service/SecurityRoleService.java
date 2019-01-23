package com.yr.service;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Role;

public interface SecurityRoleService {
	
	public Boolean add(Role role);
	
	public Boolean update(Role role);
	
	public Boolean delete(Role role);
	
	public Page<Role> query(Page<Role> page);
	
	public Role get(int id);
	
	public List<Role> userRole(int id);
	
	public List<Role> select();
	
	public Boolean givePower(String roleIds[],int ids); // 因为是将角色id传入过来,将权限赋给某个角色
	
	public List<Role> findAll();
}
