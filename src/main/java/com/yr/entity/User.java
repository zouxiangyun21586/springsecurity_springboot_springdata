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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 用户
 */
@Entity
@Table(name = "securityuser")
@DynamicUpdate(true) // 修改部分字段
public class User implements Serializable{
    private int id;
    private String userName;// 姓名
    private String account;// 账号
    private String passWord;// 密码
    private Set<Role> roles = new HashSet<Role>();// 拥有的角色
    private String roleSet = "";

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="username")
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name="password")
    public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@ManyToMany( fetch = FetchType.LAZY) // 放弃维护关系,转交给角色中的 userList 属性来维护 mappedBy = "userList",
	@JoinTable(name = "security_ur", 
		joinColumns = { @JoinColumn(name = "user_id") }, 
    	inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Transient // 不写入到数据库中
	public String getRoleSet() {
		for (Role role : roles) {
			roleSet = roleSet + role.getRoleName() + ","; // 多数据的时候用 , 分隔
		}
		if(roleSet != null && !roleSet.equals("")){
			roleSet = roleSet.substring(0,(roleSet.length()-1)); // 不为空的时候 从0下标取值 ,将
		}
		return roleSet;
	}

	public void setRoleSet(String roleSet) {
		this.roleSet = roleSet;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", account=" + account + ", passWord=" + passWord
				+ ", roles=" + roles + "]";
	}

}