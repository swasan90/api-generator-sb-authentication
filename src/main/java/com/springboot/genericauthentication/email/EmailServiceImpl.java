/**
 * Class to implement Email Service
 */
package com.springboot.genericauthentication.email;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.springboot.genericauthentication.auth.service.AuthenticationServiceImpl;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.sendgrid.*;

/**
 * @author swathy
 *
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	/**
	 * Function to implement send email message.
	 * @param Mail, Request,SendGrid
	 * 
	 */

	@Override
	public void sendEmailMessage(Mail mail, Request req, SendGrid sendgrid) throws IOException, MailErrorException {
		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			Response res = sendgrid.api(req);
			System.out.println(res.getStatusCode());
			System.out.println(res.getBody());
			System.out.println(res.getHeaders());
			logger.info("Registration mail sent");
		} catch (IOException exception) {
			throw new MailErrorException(exception.getMessage());
		}
	}

}
