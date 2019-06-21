/**
 * Class to implement the password methods
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

import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.models.ResetPassword;
import com.springboot.genericauthentication.models.ResponseMessage;
import com.springboot.genericauthentication.password.service.PasswordService;
import com.springboot.genericauthentication.token.service.TokenService;
 
/**
 * @author swathy
 *
 */
@RestController
public class PasswordController {

	private  Logger logger = LoggerFactory.getLogger(PasswordController.class);
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private TokenService tokenService;

	private ResponseMessage res;

	public PasswordController() {
		this.res = new ResponseMessage();
	}

	/**
	 * Function to implement the forgot password process
	 * @param user
	 * @return
	 * @throws MailErrorException
	 * @throws IOException
	 */
	@PostMapping(value = "/forgotPassword", produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseMessage> forgotPassword(@RequestBody AuthUser user)
			throws MailErrorException, IOException {
		if (passwordService.forgotPassword(user.getEmail())) {
			logger.info("Forgot password token link has been sent to the user");
			return new ResponseEntity<ResponseMessage>(
					this.res.setMessage("Email has been sent to your given email id for resetting password",true),
					HttpStatus.OK);
		} else {
			logger.info("User's email id is not registered /activated");
			return new ResponseEntity<ResponseMessage>(
					this.res.setMessage("Email id does not exist (or) you may have not activated your account",false),
					HttpStatus.UNAUTHORIZED);
		}

	}
	/**
	 * Function to validate the user's password token link.
	 * @param token
	 * @return boolean
	 */
	@GetMapping(value ="/reset")
	public ResponseEntity<ResponseMessage> validatePasswordLink(@Valid @RequestParam(name = "token") String token){
		logger.info("Validating token in forgot password");
		boolean result = tokenService.validateToken(token);
		return new ResponseEntity<ResponseMessage>(this.res.setStatus(result),HttpStatus.OK);		
	}
	
	/**
	 * Function to reset the user's password .
	 * @param user
	 * @return ResponseEntity
	 * @throws IOException
	 */
	@PostMapping(value="/reset",consumes="application/json",produces="application/json")
	public ResponseEntity<ResponseMessage> resetPassword(@Valid @RequestBody ResetPassword resetPasswordO) throws IOException{
		if(passwordService.resetPassword(resetPasswordO)) {
			return new ResponseEntity<ResponseMessage>(this.res.setMessage("Your password has been reset.Kindly login with your new password",true),HttpStatus.OK);
		}else {
			return new ResponseEntity<ResponseMessage>(this.res.setMessage("Unable to reset your password.",false),HttpStatus.FORBIDDEN);
		}
	}
}
