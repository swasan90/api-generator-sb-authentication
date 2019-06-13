/**
 * Exception to catch error while sending mail
 */
package com.springboot.genericauthentication.exception;

/**
 * @author swathy
 *
 */
public class MailErrorException extends Exception {

	 
	private static final long serialVersionUID = 1L;
	
	public MailErrorException(String message) {
		super(message);
	}

}
