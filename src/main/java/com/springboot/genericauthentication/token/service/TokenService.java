/**
 * Interface to define methods for token service
 */
package com.springboot.genericauthentication.token.service;

/**
 * @author swathy
 *
 */
public interface TokenService {
	
	//Function definition to validate user's token
	boolean validateToken(String token);

}
