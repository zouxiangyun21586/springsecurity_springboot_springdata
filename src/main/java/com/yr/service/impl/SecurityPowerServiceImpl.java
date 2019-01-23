package com.yr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yr.dao.SecurityPowerDao;
import com.yr.entity.Page;
import com.yr.entity.Power;
import com.yr.service.SecurityPowerService;

@Service
public class SecurityPowerServiceImpl implements SecurityPowerService{

	@Autowired
	private SecurityPowerDao securityPowerDao;
			

	@Transactional
	@Override
	public Boolean add(Power Power) {
		try {
			securityPowerDao.add(Power);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@Override
	public Boolean update(Power Power) {
		try {
			securityPowerDao.update(Power);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@Override
	public Boolean delete(Power Power) {
		try {
			int a = securityPowerDao.delete(Power);
			if(a == 1){
				return false;
			}else if(a == 2){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<Power> query(Page<Power> page) {
		page = securityPowerDao.query(page);
		return page;
	}

	@Override
	public Power get(int id) {
		Power listPower = securityPowerDao.get(id);
		return listPower;
	}
	
	@Override
	public List<Power> rolePower(int id) {
		List<Power> listPower = securityPowerDao.rolePower(id);
		return listPower;
	}
	
	@Transactional
	@Override
	public Boolean givePower(String rids[], int ids) {
		try{
			securityPowerDao.givePower(rids, ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Power> findAll() {
		return securityPowerDao.findAll();
	}

	@Override
	public List<Power> select() {
		return securityPowerDao.select();
	}

//	@Override
//	public List<Power> select(Power power) {
//		return securityPowerDao.select(power);
//	}

	@Override
	public List<Power> AuthorTag(Power power) {
		return securityPowerDao.AuthorTag(power);
	}

}
