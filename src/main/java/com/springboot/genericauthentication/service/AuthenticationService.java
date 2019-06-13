/**
 * Interface to define the authentication methods
 */
package com.springboot.genericauthentication.service;

import java.io.IOException;

import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.User;

/**
 * @author swathy
 *
 */
public interface AuthenticationService {
	
	boolean registerUser(User user) throws EntityFoundException,MailErrorException, IOException;
}
