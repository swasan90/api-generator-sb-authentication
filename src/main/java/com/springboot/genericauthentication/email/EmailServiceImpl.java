/**
 * Class to implement Email Service
 */
package com.springboot.genericauthentication.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.springboot.genericauthentication.exception.MailErrorException;

/**
 * @author swathy
 *
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void sendEmailMessage(String to, String subject, String text) throws MailErrorException {
		 try {
			 SimpleMailMessage message = new SimpleMailMessage();
			 message.setTo(to);
			 message.setSubject(subject);
			 message.setText(text);
			 
			 emailSender.send(message);
		 }catch(MailException exception) {
			 throw new MailErrorException(exception.getMessage());
		 }		
	}
	
	

}
