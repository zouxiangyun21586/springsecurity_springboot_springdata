package com.yr.service;

import java.util.List;

import com.yr.entity.Page;
import com.yr.entity.Power;

/**
 * 权限 service 接口
 * @author zxy
 * 2018年4月11日 下午7:23:21 
 *
 */
public interface SecurityPowerService {
	
	public Boolean add(Power Power);
	
	public Boolean update(Power Power);
	
	public Boolean delete(Power Power);
	
	public Page<Power> query(Page<Power> page);
	
	public Power get(int id);
	
	public List<Power> rolePower(int id);
	
	public Boolean givePower(String rIds[],int ids);
	
	public List<Power> findAll();
	
	public List<Power> select();
	
//	public List<Power> select(Power power);
	
	public List<Power> AuthorTag(Power power);
}
