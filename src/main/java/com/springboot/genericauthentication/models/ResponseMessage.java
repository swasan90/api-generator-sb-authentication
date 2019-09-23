/**
 * Class to define the response message
 */
package com.springboot.genericauthentication.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author swathy
 *
 */
@JsonInclude(Include.NON_NULL)
public class ResponseMessage {

	private String message;

	private boolean status;

	private JwtToken tokenObj;

	public ResponseMessage() {

	}

	public ResponseMessage setMessage(JwtToken token) {
		this.tokenObj = token;
		return this;
	}

	public ResponseMessage setMessage(String message, boolean status) {
		this.message = message;
		this.status = status;
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

	public JwtToken getTokenObj() {
		return tokenObj;
	}

}
