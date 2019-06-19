/**
 * Interface to define mail sending methods
 */
package com.springboot.genericauthentication.email;

import java.io.IOException;

import com.sendgrid.Mail;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.springboot.genericauthentication.exception.MailErrorException;

/**
 * @author swathy
 *
 */
public interface EmailService {

	// Function declaration to send email message
	void sendEmailMessage(Mail mail, Request request, SendGrid sendgrid) throws IOException, MailErrorException;
}
