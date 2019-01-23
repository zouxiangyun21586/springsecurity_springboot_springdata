package com.yr.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密工具类
 * @author zxy-un
 * 
 * 2018年8月15日 上午9:14:32
 */
public class QuickPasswordEncodingGenerator {

	public static void main(String[] args) {
		QuickPasswordEncodingGenerator qe = new QuickPasswordEncodingGenerator();
		String password = "111111";
		String pass = qe.encod(password);
		System.out.println(pass);
	}
	
	public String encod(String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pass = passwordEncoder.encode(password);
		return pass;
	}

}
