package com.yr.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 角色
 */
@Entity
@Table(name = "securityrole")
@DynamicUpdate(true)
public class Role implements Serializable{

	private int id;
	private String roleName;// 角色名称
	private String roleCode;// 角色代码
	private Set<Power> resourceList = new HashSet<>();
//	private String powerSet = "";
	//删除权限时,如果有角色在使用就不能进行删除.如果没角色使用可以进行删除
	//删除角色时,如果有用户在使用就不能进行删除.如果没用户使用可以进行删除
	//删除用户一定可以删除
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="rolename")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name="rolecode")
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@ManyToMany( fetch = FetchType.LAZY) // 放弃维护关系,转交给角色中的 roleList 属性来维护
	@JoinTable(name = "security_pr", 
	    joinColumns = { @JoinColumn(name = "role_id") }, 
	    inverseJoinColumns = { @JoinColumn(name = "power_id") })
	public Set<Power> getResourceList() {
		return resourceList;
	}

	public void setResourceList(Set<Power> resourceList) {
		this.resourceList = resourceList;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleCode=" + roleCode + ", resourceList=" + resourceList + "]";
	}

}