/**
 * Class to define the response message
 */
package com.springboot.genericauthentication.models;

import org.springframework.http.HttpStatus;

/**
 * @author swathy
 *
 */

public class ResponseMessage {

	private String message;

	private HttpStatus status;

	public ResponseMessage() {

	}

	public ResponseMessage setMessage(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ResponseMessage setMessage(String message) {
		this.message = message;
		return this;

	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
