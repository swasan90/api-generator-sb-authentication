package com.springboot.genericauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.genericauthentication.models.JwtToken;

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
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	
	@Bean
	  RedisTemplate<String, JwtToken> redisTemplate(){
	    RedisTemplate<String,JwtToken> redisTemplate = new RedisTemplate<String, JwtToken>();
	    redisTemplate.setConnectionFactory(jedisConnectionFactory());
	    return redisTemplate;
	  }
 
	 
}
