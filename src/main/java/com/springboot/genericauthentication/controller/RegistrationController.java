/**
 * Class to register a new user
 */
package com.springboot.genericauthentication.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.genericauthentication.auth.service.AuthenticationService;
import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.models.ResponseMessage;
import com.springboot.genericauthentication.token.service.TokenService;

/**
 * @author swathy
 *
 */
@RestController
public class RegistrationController {

	private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private AuthenticationService authService;

	@Autowired
	private TokenService tokenService;

	private ResponseMessage res;

	public RegistrationController() {
		this.res = new ResponseMessage();
	}

	/**
	 * Function to register a user
	 * 
	 * @param user
	 * @return
	 * @throws EntityFoundException
	 * @throws MailErrorException
	 * @throws IOException
	 */
	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ResponseMessage> registerAccount(@Valid @RequestBody AuthUser user)
			throws EntityFoundException, MailErrorException, IOException {
		if (authService.registerUser(user)) {
			logger.info("Successfully created user account");
			return new ResponseEntity<ResponseMessage>(
					this.res.setMessage("Successfully created account",true), HttpStatus.CREATED);
		} else {
			logger.info("Unable to create user account");
			return new ResponseEntity<ResponseMessage>(
					this.res.setMessage("Email id exist.Cannot creat account",false),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Function to activate account upon clicking the activation link
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/activate")
	public ResponseEntity<ResponseMessage> activateAccount(@Valid @RequestParam(name = "token") String token) {		
		if (tokenService.validateToken(token)) {			 
			logger.info("Successfully validated token");
			
			return new ResponseEntity<ResponseMessage>(this.res
					.setMessage("Your account has been activated.Please login with your credentials",true),
					HttpStatus.OK);
		} else {
			logger.info("Unable to activate user account");
			return new ResponseEntity<ResponseMessage>(
					this.res.setMessage("Your token was expired.Kindly register again",false),
					HttpStatus.UNAUTHORIZED);
		}
	}

}
