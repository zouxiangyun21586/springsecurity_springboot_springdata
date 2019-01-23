package com.yr.service;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.User;

public interface SecurityUserService {

	public Boolean add(User user);
	
	public Boolean update(User user);
	
	public Boolean delete(int id);
	
	public Page<User> select(Page<User> page);
	
	public List<User> select();
	
	public User get(int id);
	
	public User loadUserByAccount(String account);
}
