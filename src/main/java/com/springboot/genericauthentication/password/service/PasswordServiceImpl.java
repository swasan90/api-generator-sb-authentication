/**
 * Class to implement password service methods
 */
package com.springboot.genericauthentication.password.service;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendgrid.Mail;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.springboot.genericauthentication.email.EmailService;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
import com.springboot.genericauthentication.token.service.TokenService;

/**
 * @author swathy
 *
 */
@Service("passwordService")
public class PasswordServiceImpl implements PasswordService {

	private Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);

	@Autowired
	private AuthenticationRepository authRepo;

	@Autowired
	private TokenService tokenService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${sendgrid.api.key}")
	private String apiKey;

	@Autowired
	private EmailService emailService;

	public PasswordServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * Function to implement forgot password functionality
	 * 
	 * @param String
	 * @return boolean
	 * @throws IOException, MailErrorException
	 */
	@Override
	@Transactional
	public boolean forgotPassword(String email) throws IOException, MailErrorException {
		try {
			AuthUser user = authRepo.findByEmail(email);
			if (user != null && user.isEnabled()) {
				URI url = tokenService.constructTokenUrl(tokenService.generateTokenForUser(user), "reset");
				logger.info("Generated url token");

				Mail mailObj = tokenService.constructMailBody(user, url);
				emailService.sendEmailMessage(mailObj, new Request(), new SendGrid(apiKey));
				logger.info("Mail sent");

				return true;

			} else {
				logger.info("Unable to send forgot password link");
				return false;
			}
		} catch (DataIntegrityViolationException e) {
			logger.info("Catching Exception " + e.getMessage());
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * Function to reset user's password
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */

	@Override
	public boolean resetPassword(AuthUser user) throws IOException {
		try {
			AuthUser usr = authRepo.findByEmail(user.getEmail());
			if (usr != null) {
				usr.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));				
				usr.setConfirmPassword(usr.getPassword());
				authRepo.save(usr);
				logger.info("Password reset done for the user " + user.getEmail());
				return true;
			}
			logger.info("User email doesn't exist");
			return false;			
		} catch (DataIntegrityViolationException e) {
			logger.info("Catching Exception during forgot password " + e.getMessage());
			throw new IOException(e.getMessage());
		}

	}
}
