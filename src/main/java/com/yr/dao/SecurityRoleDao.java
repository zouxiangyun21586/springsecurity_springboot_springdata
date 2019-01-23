package com.yr.dao;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Role;

public interface SecurityRoleDao {
	public void add(Role role);
	
	public void update(Role role);
	
	public int delete(Role role);
	
	public Page<Role> query(Page<Role> page);
	
	public Role get(int id);
	
	public List<Role> userRole(int id);
	
	public void givePower(String roleIds[],int ids); // 因为是将角色id传入过来,将权限赋给某个角色
	
	public List<Role> findAll();
	
	public List<Role> select();
	
	public List<Role> findByUserId(int userId);
}	
