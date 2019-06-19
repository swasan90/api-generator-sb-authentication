/**
 * Interface to define methods for token service
 */
package com.springboot.genericauthentication.token.service;

import java.net.URI;

import com.sendgrid.Mail;
import com.springboot.genericauthentication.models.AuthUser;

/**
 * @author swathy
 *
 */
public interface TokenService {
	 
	
	//Function definition to validate user's token
	boolean validateToken(String token);
	
	//Function to create token for the user
	String generateTokenForUser(AuthUser user);
	
	//Function to construct URL with token for the user
	URI constructTokenUrl(String token,String action);
	
	//Function to construct message for sending email
	Mail constructMailBody(AuthUser user,URI url);
	
	 

}
