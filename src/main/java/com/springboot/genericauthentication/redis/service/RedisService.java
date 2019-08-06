package com.springboot.genericauthentication.redis.service;

import java.util.List;

import com.springboot.genericauthentication.models.JwtToken;

public interface RedisService {
	
	//Function to delete user's token from Redis once logs out.
	void deleteUserToken(String id) throws Exception;
	
	//Function to update user's token from Redis when extending the stateless session.
	boolean updateUserToken(String id);
	
	//Function to get user's token by id.
	JwtToken findTokenById(String id);
	
	Iterable<JwtToken> listAll();

}
