/**
 * Class to define the security constants variables
 */
package com.springboot.genericauthentication.jwt;

/**
 * @author swathy
 *
 */
public class SecurityConstants {
	
	public static final long EXPIRATION_TIME = 900000; //15 min
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/register";
	public static final String FORGOT_PASSWORD_URL ="/forgotPassword";
	public static final String RESET_PASSWORD_URL_PATH ="/reset";
	public static final String ACTIVATE_USER ="/activate";
	public static final String GET_JWT_TOKEN_BY_ID ="/getToken/{id}";
	public static final String List_All_Tokens="/listAllTokens";
	public static final String DELETE_TOKEN ="/deleteToken/{id}";
	
}
