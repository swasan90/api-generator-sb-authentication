/**
 * Class to define the response message
 */
package com.springboot.genericauthentication.models;

/**
 * @author swathy
 *
 */

public class ResponseMessage {

	private String message;
	
	private boolean status;

	public ResponseMessage() {

	}

	public ResponseMessage setMessage(String message) {
		this.message = message;		 
		return this;
	}
	
	public ResponseMessage setStatus(boolean status) {
		 this.status = status;		
		 return this;
		
	}
	
	public boolean getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
