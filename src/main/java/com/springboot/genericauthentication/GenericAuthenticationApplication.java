package com.springboot.genericauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GenericAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenericAuthenticationApplication.class, args);
	}

	/**
	 * Instantiating BCryptPasswordEncoder bean
	 * 
	 * @return BCryptPasswordEncoder
	 * 
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	 
 
	 
}
