package com.yr.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.yr.dao.SecurityPowerDao;
import com.yr.entity.Page;
import com.yr.entity.Power;

@Repository
public class SecurityPowerDaoImpl implements SecurityPowerDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Power power) {
		power.setUsageMethod("GET"); // 默认请求方法是 GET
		entityManager.persist(power);
	}

	@Override
	public void update(Power power) {
		Power re = new Power();
		re.setId(power.getId());
		re.setPowerName(power.getPowerName());
		re.setHttpUrl(power.getHttpUrl());
		re.setUsageMethod(power.getUsageMethod());
		entityManager.merge(re);
	}

	@Override
	public int delete(Power power) {
		Query query = entityManager.createNativeQuery("select count(role_id) from security_PR where Power_id =" + power.getId());
		BigInteger big = (BigInteger) query.getSingleResult();
		int uid = big.intValue();
		if(query != null && uid != 0){ // 权限有角色关联不能删除
			return 1;
		}else{
			Query qu = entityManager.createNativeQuery("delete from securitypower where id =" + power.getId());
			qu.executeUpdate();
			return 2;
		}
	}

	@Override
	public Page<Power> query(Page<Power> page) {
		//                        10            5
		//  开始下标                         每页多少条记录                      第几页
		int startIndex = page.getPageSize() * (page.getPage()-1);
		int pageSize = page.getPageSize();
		Query q = entityManager.createQuery("from Power ").setFirstResult(startIndex).setMaxResults(pageSize);

		//查询总数
		Query q1 = entityManager.createNativeQuery("select count(*) from securitypower");
		BigInteger count = (BigInteger) q1.getSingleResult();
		page.setPageSizeCount(count.intValue());
		
		List<Power> listPower = q.getResultList();
		page.setT(listPower);
		return page;
	}

	@Override
	public Power get(int id) {
		Query q = entityManager.createQuery("from Power where id = "+id);
		Power listUser = (Power)q.getSingleResult();
		return listUser;
	}
	
	@Override
	public List<Power> rolePower(int id) {
		Query q = entityManager.createNativeQuery("select * from securitypower where id in (select power_id from security_PR where role_id = "+id+")");
		List<Object[]> listObject = q.getResultList();
		
		List<Power> list = new ArrayList<Power>();
		for (Object[] obj : listObject) {
			Power Power = new Power();
			Power.setId(Integer.valueOf((obj[0]).toString()));
			Power.setHttpUrl((String) obj[1]);
			Power.setPowerName((String) obj[2]);
			Power.setUsageMethod((String)obj[3]);
			
			list.add(Power);
		}
    	
     	return list;
	}

	/**
	 * 赋权
	 * 
	 * @author zxy
	 * @param role 角色
	 * @param ids角色权限
	 * @param rids 权限id
	 * 2018年4月14日 上午9:45:36 
	 * 注意: 这里的两条sql语句应分为
	 * 		[删除中间表时写在角色逻辑层,插入中间表时应写在权限逻辑层(因为权限那边是维护关系,插入才会有关联)]
	 *
	 */
	@Override
	public void givePower(String rids[],int id) {
		Query q = entityManager.createNativeQuery("delete from security_PR where role_id = " + id ); // 删除中间表与传来的角色id相同值(清空某角色的权限)
		q.executeUpdate(); // 执行修改
		
		for (String ids : rids) {
			Query q1 = entityManager.createNativeQuery("insert into security_PR values(?,?)");
			q1.setParameter(1, id); // 与数据库顺序对应,或者插入语句中定义
			q1.setParameter(2, ids);
			q1.executeUpdate();
		}
	}

	@Override
	public List<Power> findByRoleId(int roleId) {
		// 关联查询出数据 (获取角色id ,查询所对应的所有权限) -- 本地sql不能使用具名参数会识别不了
		Query q = entityManager.createNativeQuery("select DISTINCT p.* from securityrole r LEFT JOIN security_pr pr on r.id = pr.role_id LEFT JOIN securitypower p on p.id = pr.power_id where r.id=?").setParameter(1, roleId);
		List<Object[]> listObj = q.getResultList();
		List<Power> listPower = new ArrayList<Power>();
		for (Object[] obj : listObj) {
			Power power = new Power();
			power.setId(Integer.valueOf(obj[0].toString()));
			power.setHttpUrl((String)obj[1]);
			power.setPowerName((String)obj[2]);
			power.setUsageMethod((String)obj[3]);
			
			listPower.add(power);
		}
		return listPower;
	}

	@Override
	public List<Power> findAll() {
		Query q = entityManager.createNativeQuery("select powername from securitypower");
		List<Power> listPower = q.getResultList();
		return listPower;
	}
	
	public List<Power> select(){
		Query q = entityManager.createQuery("from Power");
		List<Power> listPower = q.getResultList();
		return listPower;
	}

//	@Override
//	public List<Power> select(Power power) {
//		Query q = entityManager.createNativeQuery("select powername,type from securitypower");
//		List<Power> listPower = q.getResultList();
//		return listPower;
//	}
	
	@Override
	public List<Power> AuthorTag(Power power) {
		Query q = entityManager.createNativeQuery("SELECT p.* FROM `securityuser` u ,securityrole r, securitypower p ,security_ur ur, security_pr pr where u.id = ur.user_id and r.id = ur.role_id and p.id = pr.power_id and r.id = pr.role_id and u.account = ? and p.httpUrl like ? and p.usagemethod = ?")
								.setParameter(1, power.getUserName()).setParameter(2, power.getHttpUrl()).setParameter(3, power.getUsageMethod());
		List<Power> listPower = q.getResultList();
		return listPower;
	}
}
