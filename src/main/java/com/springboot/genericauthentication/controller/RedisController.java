/**
 * 
 */
package com.springboot.genericauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.genericauthentication.models.JwtToken;
import com.springboot.genericauthentication.models.ResponseMessage;
import com.springboot.genericauthentication.redis.service.RedisService;

/**
 * @author swathy
 *
 */
@RestController
public class RedisController {

	private ResponseMessage res;

	public RedisController() {
		this.res = new ResponseMessage();
	}

	@Autowired
	private RedisService redisService;

	@GetMapping(value = "/getToken/{id}", produces = "application/json")
	public ResponseEntity<ResponseMessage> getUserToken(@PathVariable String id) {
		JwtToken token = this.redisService.findTokenById(id);
		if (token != null) {
			return new ResponseEntity<ResponseMessage>(this.res.setMessage(token), HttpStatus.OK);
		}
		return new ResponseEntity<ResponseMessage>(this.res.setMessage(null), HttpStatus.OK);

	}

	@GetMapping(value = "listAllTokens", produces = "application/json")
	public Iterable<JwtToken> listAll() {
		Iterable<JwtToken> tokens = this.redisService.listAll();
		return tokens;

	}

	@DeleteMapping(value = "deleteToken/{id}", produces = "application/json")
	public ResponseEntity<ResponseMessage> deleteUserToken(@PathVariable String id) throws Exception {
		this.redisService.deleteUserToken(id);
		return new ResponseEntity<ResponseMessage>(this.res.setStatus(true), HttpStatus.OK);
	}

}
