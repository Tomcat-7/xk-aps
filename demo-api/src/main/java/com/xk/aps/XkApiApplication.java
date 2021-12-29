package com.xk.aps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 系统启动类
 * @author xkes
 * @date 2020-2-20
 */

@SpringBootApplication
public class XkApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(XkApiApplication.class, args);
	}

}
