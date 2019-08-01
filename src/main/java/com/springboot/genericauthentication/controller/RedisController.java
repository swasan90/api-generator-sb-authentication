/**
 * 
 */
package com.springboot.genericauthentication.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.genericauthentication.models.JwtToken;
 

/**
 * @author swathy
 *
 */
@RestController
public class RedisController {
	
//	@Autowired
//	private JwtRepository jwtRepo;
	
	@GetMapping(value="/getToken/{id}",produces = "application/json")
	public Optional<JwtToken> getUserToken(@PathVariable String id) {
		return null;
//		System.out.println(this.jwtRepo.findById(UUID.fromString(id)));
//		return this.jwtRepo.findById(UUID.fromString(id));
	}

}
