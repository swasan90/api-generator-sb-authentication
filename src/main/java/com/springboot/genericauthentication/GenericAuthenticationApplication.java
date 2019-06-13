package com.springboot.genericauthentication;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GenericAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenericAuthenticationApplication.class, args);
	}
	
	/**
	 * Instantiating BCryptPasswordEncoder bean
	 * @return BCryptPasswordEncoder
	 * 
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		
		//Credentials
		mailSender.setUsername("technojuggernaut@gmail.com");
		mailSender.setPassword("asd123qwe456");
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol","smtp");
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.debug","true");
		
		
		return mailSender;
	}
	
}
