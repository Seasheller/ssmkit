package com.ssmkit.admin;

import com.ssmkit.common.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class AdminApplication {

	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}
