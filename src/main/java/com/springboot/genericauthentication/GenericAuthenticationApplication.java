package com.springboot.genericauthentication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.genericauthentication.exception.IOCustomException;

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

	/**
	 * Bean configuration for Java Mail send
	 * 
	 * @return
	 * @throws IOCustomException
	 */

	@Bean
	public JavaMailSender getJavaMailSender() throws IOCustomException {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties apProps = getMailAppProperties();
		mailSender.setHost(apProps.getProperty("spring.mail.host"));
		mailSender.setPort(Integer.parseInt(apProps.getProperty("spring.mail.port")));

		// Credentials
		mailSender.setUsername(apProps.getProperty("spring.mail.username"));
		mailSender.setPassword(apProps.getProperty("spring.mail.password"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	/**
	 * Function to get application properties for mail
	 * 
	 * @return
	 * @throws IOCustomException
	 */
	private Properties getMailAppProperties() throws IOCustomException {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "application.properties";
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(appConfigPath));
		} catch (IOException e) {
			throw new IOCustomException(e.getMessage());
		}
		return props;

	}

}
