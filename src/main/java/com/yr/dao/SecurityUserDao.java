package com.yr.dao;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.User;

public interface SecurityUserDao {
	
	public void add(User user);
	
	public void update(User user);
	
	public void delete(int id);
	
	public Page<User> select(Page<User> page);
	
	public List<User> select();
	
	public User get(int id);
	
	public User loadUserByAccount(String account);
}
