package com.springboot.genericauthentication.redis.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.genericauthentication.models.JwtToken;
import com.springboot.genericauthentication.repository.RedisRepository;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
	
	@Autowired
	private RedisRepository redisRepository;

	@Override
	public void deleteUserToken(String id) throws Exception {
		try {
			if(this.findTokenById(id) !=null) {
				this.redisRepository.deleteById(id);
			}
		}catch(EntityNotFoundException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public boolean updateUserToken(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JwtToken findTokenById(String id) {
		return this.redisRepository.findById(id).orElse(null);
	}

	@Override
	public Iterable<JwtToken> listAll() {
		return this.redisRepository.findAll();
	}
	
	

}
