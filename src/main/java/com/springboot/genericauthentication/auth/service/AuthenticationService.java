/**
 * Interface to define the authentication methods
 */
package com.springboot.genericauthentication.auth.service;

import java.io.IOException;

import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;

/**
 * @author swathy
 *
 */
public interface AuthenticationService {

	// Function declaration to register user.
	boolean registerUser(AuthUser user) throws EntityFoundException, MailErrorException, IOException;

}
