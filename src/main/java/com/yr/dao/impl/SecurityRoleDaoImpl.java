package com.yr.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.yr.dao.SecurityRoleDao;
import com.yr.entity.Page;
import com.yr.entity.Power;
import com.yr.entity.Role;

@Repository
public class SecurityRoleDaoImpl implements SecurityRoleDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 将权限查出,并且将查出的权限赋权给刚添加的角色
	 * 
	 * @author zxy
	 * @param role
	 * 2018年4月14日 上午9:39:42 
	 *
	 */
	@Override
	public void add(Role role) {
		Power power = entityManager.find(Power.class, 6); // 登出权限
		Power power1 = entityManager.find(Power.class, 11); // 添加用户权限
		Power power2 = entityManager.find(Power.class, 14); // 查询用户权限
		Set<Power> setPower = new HashSet<>();
		setPower.add(power);
		setPower.add(power1);
		setPower.add(power2);
		role.setResourceList(setPower);
		entityManager.persist(role);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(Role role) {
		Role r = entityManager.find(Role.class, role.getId());
		
		r.setRoleName(role.getRoleName());
		r.setRoleCode(role.getRoleCode());
	}

	@Override
	public int delete(Role role) { // 如果角色没有用户使用可以删除 , 有用户使用不能删除
		Query query = entityManager.createNativeQuery("select count(user_id) from security_UR where role_id =" + role.getId());
		BigInteger big = (BigInteger) query.getSingleResult();
		int uid = big.intValue();
		Query q = entityManager.createNativeQuery("select count(power_id) from security_PR where role_id =" + role.getId()); // 计算中间表中有几条值
		BigInteger count = (BigInteger) q.getSingleResult();
		int co = count.intValue();
		if(query !=null && uid != 0){
			return 1;
		}else if(q!=null && co != 0){ // 说明有值
			Query qy = entityManager.createNativeQuery("delete from security_PR where role_id =" + role.getId()); // 根据角色id删除不会有需循环取值问题
			Query qu = entityManager.createNativeQuery("delete from securityrole where id =" + role.getId());
			qy.executeUpdate();
			qu.executeUpdate();
			return 2;
		}else{
			Query qy = entityManager.createNativeQuery("delete from securityrole where id =" + role.getId());
			qy.executeUpdate();
			return 3;
		}
	}

	@Override
	public Page<Role> query(Page<Role> page) {
		//                        10            5
		//  开始下标                         每页多少条记录                      第几页
		int startIndex = page.getPageSize() * (page.getPage()-1);
		int pageSize = page.getPageSize();
		Query q = entityManager.createQuery("from Role ").setFirstResult(startIndex).setMaxResults(pageSize);

		//查询总数
		Query q1 = entityManager.createNativeQuery("select count(*) from securityrole");
		BigInteger count = (BigInteger) q1.getSingleResult();
		page.setPageSizeCount(count.intValue());
		
		List<Role> listPower = q.getResultList();
		page.setT(listPower);
		return page;
	}

	@Override
	public Role get(int id) {
		Query q = entityManager.createQuery("from Role where id = "+id);
		Role listRole = (Role)q.getSingleResult();
		return listRole;
	}
	
	@Override
	public List<Role> userRole(int id) {
		// 将用户角色表中的角色id查出再将角色查出,返回角色的属性
		Query q = entityManager.createNativeQuery("select * from securityrole where id in (select role_id from security_UR where user_id ="+id+")");
		List<Object[]> listRole = q.getResultList();
		
		List<Role> list = new ArrayList<Role>();
		for (Object[] obj : listRole) {
			Role role = new Role();
			role.setId(Integer.valueOf(obj[0].toString()));
			role.setRoleCode((String)obj[1]);
			role.setRoleName((String)obj[2]);
			
			list.add(role);
		}
		return list;
	}
	
	@Override
	public void givePower(String roleIds[], int id) {
		Query q = entityManager.createNativeQuery("delete from security_UR where user_id = ?").setParameter(1, id); // 删除中间表与传来的角色id相同值(清空某角色的权限)
		q.executeUpdate();
		
		for (String ids : roleIds) {
			Query q1 = entityManager.createNativeQuery("insert into security_UR values(?,?)"); // 重新赋权,将某角色的所拥有的权限插入
			q1.setParameter(1, id); // 角色
			q1.setParameter(2, ids); // 权限
			q1.executeUpdate();
		}
	}

	/**
	 * 查询所有角色名
	 */
	@Override
	public List<Role> findAll() {
		Query q = entityManager.createNativeQuery("select rolename from securityrole");
		List<Role> listRole = q.getResultList();
		return listRole;
	}

	@Override
	public List<Role> findByUserId(int userId) {
		// 获取用户名,查询所对应的所有角色
		Query q = entityManager.createNativeQuery("select DISTINCT r.* from securityrole r LEFT JOIN security_ur ur on r.id = ur.role_id LEFT JOIN securityuser u on ur.user_id = u.id where u.id=?").setParameter(1, userId);
		List<Object[]> listObj = q.getResultList();
		List<Role> listRole = new ArrayList<Role>();
		for (Object[] obj : listObj) {
			Role role = new Role();
			role.setId(Integer.valueOf((obj[0]).toString()));
			role.setRoleName((String)obj[1]);
			role.setRoleCode((String)obj[2]);
			
			listRole.add(role);
		}
		return listRole;
	}

	@Override
	public List<Role> select() {
		Query q = entityManager.createQuery("From Role");
		List<Role> listRole = q.getResultList();
		return listRole;
	}

}