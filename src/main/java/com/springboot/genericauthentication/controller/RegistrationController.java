/**
 * Class to register a new user
 */
package com.springboot.genericauthentication.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RestController;

import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.models.ResponseMessage;
import com.springboot.genericauthentication.models.User;
import com.springboot.genericauthentication.service.AuthenticationService;

/**
 * @author swathy
 *
 */
@RestController 
public class RegistrationController {
	
	@Autowired
	private AuthenticationService authService;
	 
	
	@PostMapping(value="/register",consumes="application/json",produces="application/json")
	public ResponseEntity<ResponseMessage> registerAccount(@Valid @RequestBody User user) throws EntityFoundException{		
		ResponseMessage res = new ResponseMessage();
		if( authService.registerUser(user)) {			
			return new ResponseEntity<ResponseMessage>( res.setMessage("Successfully created account",HttpStatus.CREATED),HttpStatus.CREATED); 
		}else {
			return new ResponseEntity<ResponseMessage>( res.setMessage("Email id exist.Cannot creat account",HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST); 
		}  
	}

}
