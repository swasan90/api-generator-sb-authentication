/**
 * Class to define the security constants variables
 */
package com.springboot.genericauthentication.jwt;

/**
 * @author swathy
 *
 */
public class SecurityConstants {

	public static final String SECRET = "AuthSecretKey";
	public static final long EXPIRATION_TIME = 216_000_000; // 1 day
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/register";
	public static final String FORGOT_PASSWORD_URL ="/forgotPassword";
	public static final String RESET_PASSWORD_URL_PATH ="/reset";
	public static final String ACTIVATE_USER ="/activate";
	
}
