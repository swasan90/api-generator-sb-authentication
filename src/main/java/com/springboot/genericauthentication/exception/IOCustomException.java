/**
 * Class to define the IO Exception
 */

package com.springboot.genericauthentication.exception;

public class IOCustomException extends Exception {

	private static final long serialVersionUID = 1L;

	public IOCustomException(String message) {
		super(message);
	}

}
