/**
 * Interface to define mail sending methods
 */
package com.springboot.genericauthentication.email;

import com.springboot.genericauthentication.exception.MailErrorException;

/**
 * @author swathy
 *
 */
public interface EmailService {

	void sendEmailMessage(String to,String subject,String text ) throws MailErrorException;
}
