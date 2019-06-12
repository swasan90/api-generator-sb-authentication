/**
 * Interface to define the authentication methods
 */
package com.springboot.genericauthentication.service;

import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.models.User;

/**
 * @author swathy
 *
 */
public interface AuthenticationService {
	
	boolean registerUser(User user) throws EntityFoundException;
}
