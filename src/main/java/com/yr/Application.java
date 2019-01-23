package com.yr;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库中 权限表的 pid 字段描述: 继承自己表的id, 假如第五条数据是 All权限,而第六条数据继承第五条数据,那么第六条数据就有 All 权限.
 * Bug解决: 
 * 		如果页面出现 'JSPs only permit GET POST or HEAD' 那么即是请求方法错误 ,
 * 		需使用一个过滤器(GetMethodConvertingFilter),并且配置web.xml ,
 * 		假设还是出错那么将在jsp页面的头文件上加上 isErrorPage="true" 属性, 便会解决问题 ;
 * @author zxy-un
 * 
 * 2018年8月7日 上午9:10:42
 */
@Configuration
@ComponentScan(basePackages ="com.yr")
@SpringBootApplication
public class Application {
	
    public static void main(String[] args) {
        ConfigurableApplicationContext run = run(Application.class, args);
    }

}