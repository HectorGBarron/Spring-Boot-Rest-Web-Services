package com.training.fisrt.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.training.fisrt.app.ws.security.AppProperties;

@SpringBootApplication
public class MobileAppsWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileAppsWsApplication.class, args);
	
	
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		
		return new SpringApplicationContext();
	}

	
	@Bean(name="AppProperties")
	public AppProperties getAppProperties() {
		
		return new  AppProperties();
	}
}
