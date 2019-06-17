/**
 * Interface to define mail sending methods
 */
package com.springboot.genericauthentication.email;

import com.springboot.genericauthentication.exception.MailErrorException;

import java.io.IOException;

import com.sendgrid.*;

/**
 * @author swathy
 *
 */
public interface EmailService {

	// Function declaration to send email message
	void sendEmailMessage(Mail mail, Request request, SendGrid sendgrid) throws IOException, MailErrorException;
}
