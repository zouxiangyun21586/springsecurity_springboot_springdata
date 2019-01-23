package com.yr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yr.dao.SecurityUserDao;
import com.yr.entity.Page;
import com.yr.entity.User;
import com.yr.service.SecurityUserService;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {
	
	@Autowired
	private SecurityUserDao securityUserDao;
	
	@Transactional
	@Override
	public Boolean add(User user) {
		try {
			securityUserDao.add(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@Override
	public Boolean update(User user) {
		try {
			securityUserDao.update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@Override
	public Boolean delete(int id) {
		try {
			securityUserDao.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<User> select(Page<User> page) {
		page = securityUserDao.select(page);
		return page;
	}
	
	@Override
	public User get(int id) {
		User ListiId = securityUserDao.get(id);
		return ListiId;
	}
	
	@Override
	public List<User> select() {
		List<User> result = securityUserDao.select();
        return result;
	}

	@Override
	public User loadUserByAccount(String account) {
		User user = securityUserDao.loadUserByAccount(account);
		return user;
	}

}