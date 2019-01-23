package com.yr.testPass;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 测试类
 * @author zxy-un
 * 
 * 2018年8月2日 下午7:43:18
 */
public class QuickPasswordEncodingGenerator {

	/**
	 * 测试密码加密
	 * @author zxy-un
	 * 
	 * @param args
	 * 
	 * 上午10:48:42
	 */
	public static void main(String[] args) {
			String password = "123456";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			System.out.println(passwordEncoder.encode(password));
	}

}
