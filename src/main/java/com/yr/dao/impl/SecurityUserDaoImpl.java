package com.yr.dao.impl;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.yr.dao.SecurityUserDao;
import com.yr.entity.Page;
import com.yr.entity.Role;
import com.yr.entity.User;
import com.yr.util.QuickPasswordEncodingGenerator;

@Repository
public class SecurityUserDaoImpl implements SecurityUserDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 将角色查出,并且将查出的角色赋权给刚添加的用户
	 * 
	 * @author zxy
	 * @param user
	 * 2018年4月14日 上午9:35:13 
	 *
	 */
	@Override
	public void add(User user) {
		QuickPasswordEncodingGenerator qpe = new QuickPasswordEncodingGenerator();
		user.setPassWord(qpe.encod(user.getPassWord()));
		Role r = entityManager.find(Role.class, 4);
		Set<Role> setRole = new HashSet<>();
		setRole.add(r);
		user.setRoles(setRole);
		entityManager.persist(user);
	}

	/**
	 * 修改: 密码因为加密错误导致密码不对,所以暂停密码修改
	 */
	@Override
	public void update(User user) {
		User us = entityManager.find(User.class, user.getId());
		
		String userName = user.getUserName();
//		String passWord = user.getPassWord();
		
		
		us.setUserName(userName);
//		boolean result = passWord.matches("[0-9]+"); // 如果密码是纯数字,那么代表是修改后的密码
//		if (result == true) { // 如果是修改后的密码,那么需先加密后再添加到数据库中 
//			QuickPasswordEncodingGenerator qpe = new QuickPasswordEncodingGenerator();
//			us.setPassWord(qpe.encod(passWord));
//		} else { // 如果是旧密码那么不需要修改,直接覆盖
//			us.setPassWord(passWord);
//		}
		
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(int id) {
		Query q = entityManager.createNativeQuery("select count(role_id) from security_UR where user_id =" + id);
		BigInteger count = (BigInteger) q.getSingleResult();
		int co = count.intValue();
		if(q!=null && co != 0){ // 说明有值
			Query qy = entityManager.createNativeQuery("delete from security_UR where user_id =" + id);
			Query qu = entityManager.createNativeQuery("delete from securityuser where id =" + id);
			qy.executeUpdate();
			qu.executeUpdate();
		}else{
			Query qy = entityManager.createNativeQuery("delete from securityuser where id =" + id);
			qy.executeUpdate();
		}
	}

	/**
	 * 分页查询
	 */
	@Override
	public Page<User> select(Page<User> page){
		//      10            5
		//  开始下标                         每页多少条记录                      第几页
		int startIndex = page.getPageSize() * (page.getPage()-1);
		int pageSize = page.getPageSize();
		Query q = entityManager.createQuery("from User ").setFirstResult(startIndex).setMaxResults(pageSize);
		
		//查询总数
		Query q1 = entityManager.createNativeQuery("select count(*) from securityuser");
		BigInteger count = (BigInteger) q1.getSingleResult();
		page.setPageSizeCount(count.intValue());
		
		List<User> listResource = q.getResultList();
		page.setT(listResource);
		return page;
	}
	
	/**
	 * 查询
	 */
	@Override
	public List<User> select() {
		Query q = entityManager.createQuery("From User");
		List<User> listUser = q.getResultList();
		return listUser;
	}
	
	/**
	 * 修改回显
	 */
	@Override
	public User get(int id){
		Query q = entityManager.createQuery("from User where id = "+id);
		User listUser = (User) q.getSingleResult();
		return listUser;
	}
	
	/**
	 * 获取用户名(查询用户是否存在)
	 */
	@Override
	public User loadUserByAccount(String account) {
		Query q = entityManager.createQuery("from User where account = :account").setParameter("account", account);
		User user = (User) q.getSingleResult();
		return user;
	}
}
