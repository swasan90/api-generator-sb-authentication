/**
 * Class to define Jwt token
 */
package com.springboot.genericauthentication.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

/**
 * @author swathy
 *
 */
@RedisHash("jwtToken")
@Data
public class JwtToken {

	@Id
	private String user_id;

	private String jwtToken;

	public JwtToken() {
	};

	public JwtToken(String userId, String token) {
		super();
		this.user_id = userId;
		this.jwtToken = token;
	}
}
