package com.yr.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 资源
 */
@Entity
@Table(name = "securitypower")
public class Power implements Serializable{

    private int id;
    private String powerName; // 资源名称
    private String httpUrl; // 资源路径
    private String usageMethod; // 使用方法
    private String userName;
   
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="powername")
	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

	@Column(name="httpurl")
	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	@Column(name="usagemethod")
	public String getUsageMethod() {
		return usageMethod;
	}

	public void setUsageMethod(String usageMethod) {
		this.usageMethod = usageMethod;
	}
	
	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Power [id=" + id + ", powerName=" + powerName + ", httpUrl=" + httpUrl + ", usageMethod=" + usageMethod
				+ "]";
	}
	
}