package com.yr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yr.dao.SecurityRoleDao;
import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.service.SecurityRoleService;

@Service
public class SecurityRoleServiceImpl implements SecurityRoleService{

	@Autowired
	private SecurityRoleDao securityRoleDao;
	
	@Transactional
	@Override
	public Boolean add(Role role) {
		try {
			securityRoleDao.add(role);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@Override
	public Boolean update(Role role) {
		try {
			securityRoleDao.update(role);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@Override
	public Boolean delete(Role role) {
		try {
			int o = securityRoleDao.delete(role);
			if(o == 1){
				return false;
			}else if(o == 2){
				return true;
			}else if(o == 3){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<Role> query(Page<Role> page) {
		page = securityRoleDao.query(page);
		return page;
	}

	@Override
	public Role get(int id) {
		Role listRole = securityRoleDao.get(id);
		return listRole;
	}

	@Override
	public List<Role> userRole(int id) {
		List<Role> listRole = securityRoleDao.userRole(id);
		return listRole;
	}

	@Transactional
	@Override
	public Boolean givePower(String roleIds[], int ids) {
		try{
			securityRoleDao.givePower(roleIds, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Role> findAll() {
		return securityRoleDao.findAll();
	}

	@Override
	public List<Role> select() {
		return securityRoleDao.select();
	}
}
