/**
 * Class to define Jwt token
 */
package com.springboot.genericauthentication.models;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

/**
 * @author swathy
 *
 */
 @RedisHash("jwtToken") 
public class JwtToken  {
	 
	 @Id String user_id;

	private String jwtToken;
	
	public JwtToken() {
	};

	public JwtToken(String userId, String token) {
		super();
		this.user_id = userId;
		this.jwtToken = token;
	}
}
