/**
 * Interface to define methods for token service
 */
package com.springboot.genericauthentication.token.service;

/**
 * @author swathy
 *
 */
public interface TokenService {
	
	boolean validateToken(String token);

}
