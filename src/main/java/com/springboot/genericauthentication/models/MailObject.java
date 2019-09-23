/**
 * Java POJO class to define the mail object
 */
package com.springboot.genericauthentication.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author swathy
 *
 */
@Data
public class MailObject {

	private String subject;

	private String message;

	@Email
	@NotBlank(message = "Email id is mandatory")
	private String to;

	private String greeting;

	public MailObject() {
	};

	public MailObject(String to, String subject, String greeting, String message) {
		this.to = to;
		this.subject = subject;
		this.greeting = greeting;
		this.message = message;
	}

}
