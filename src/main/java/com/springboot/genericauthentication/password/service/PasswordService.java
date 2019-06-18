/**
 * Interface to define methods for password service
 */
package com.springboot.genericauthentication.password.service;

import java.io.IOException;

import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;

/**
 * @author swathy
 *
 */
public interface PasswordService {
	
	//Function to define forgot password method
	boolean forgotPassword(String email)  throws MailErrorException, IOException;
	
	//Function to define reset password method
	public boolean resetPassword(AuthUser user) throws IOException;

}
