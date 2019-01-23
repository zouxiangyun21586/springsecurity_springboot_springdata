package com.yr.dao;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Power;
import com.yr.entity.Role;

public interface SecurityPowerDao {
	public void add(Power Power);
	
	public void update(Power Power);
	
	public int delete(Power Power);
	
	public Page<Power> query(Page<Power> page);
	
	public Power get(int id);
	
	public List<Power> rolePower(int id);
	
	public void givePower(String rids[],int ids); // 因为是将角色id传入过来,将权限赋给某个角色
	
	public List<Power> findByRoleId(int roleId);
	
	public List<Power> findAll();
	
	public List<Power> select();
	
//	public List<Power> select(Power power);
	
	public List<Power> AuthorTag(Power power);
}	
