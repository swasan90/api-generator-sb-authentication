/**
 * Interface to define mail sending methods
 */
package com.springboot.genericauthentication.email;

/**
 * @author swathy
 *
 */
public interface EmailService {

	void sendEmailMessage(String to,String subject,String text );
}
