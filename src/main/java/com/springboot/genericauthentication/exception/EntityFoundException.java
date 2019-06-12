/**
 * Exception class to raise a user defined exception
 */
package com.springboot.genericauthentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author swathy
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason ="Email Id exists.Cannot create duplicate entity.")
public class EntityFoundException extends Exception {

	 
	private static final long serialVersionUID = 1L;
	
	public EntityFoundException(String message) {		
		super(message); 		 
	}

}
