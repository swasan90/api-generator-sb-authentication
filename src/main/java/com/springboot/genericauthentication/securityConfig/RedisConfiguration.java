package com.springboot.genericauthentication.securityConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {
	
	@Value("${spring.redis.host}")
	private String redisHost;

	
	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisHost);
		
		LettuceClientConfigurationBuilder lettuceBuilder = LettuceClientConfiguration.builder();
		LettuceConnectionFactory lettuceFactory = new LettuceConnectionFactory(
				redisStandaloneConfiguration,lettuceBuilder.build());
		return lettuceFactory;
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		return template;
	}
	
//	@Bean
//	  RedisTemplate<String, JwtToken> redisTemplate(){
//	    RedisTemplate<String,JwtToken> redisTemplate = new RedisTemplate<String, JwtToken>();
//	    redisTemplate.setConnectionFactory(jedisConnectionFactory());
//	    return redisTemplate;
//	  }
//
}
