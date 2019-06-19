/**
 * Class to implement authentication service methods.
 */
package com.springboot.genericauthentication.auth.service;

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

import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.springboot.genericauthentication.email.EmailService;
import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
import com.springboot.genericauthentication.token.service.TokenService;

/**
 * @author swathy
 *
 */
@Service("authService")
public class AuthenticationServiceImpl implements AuthenticationService {

	private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	private AuthenticationRepository authRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private TokenService tokenService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${sendgrid.api.key}")
	private String apiKey;

	@Value("${sendgrid.from.mail}")
	private Email from;

	public AuthenticationServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 *
	 * Function to register the user , generating token and sending mail to the user
	 * with token
	 * 
	 * @param user
	 * @return boolean
	 */

	@Override
	@Transactional
	public boolean registerUser(AuthUser user) throws EntityFoundException, IOException, MailErrorException {
		AuthUser usr = authRepo.findByEmail(user.getEmail());
		try {
			if (usr == null) {
				AuthUser newUser = new AuthUser(user.getFirstName(), user.getLastName(), user.getEmail(),
						bCryptPasswordEncoder.encode(user.getPassword()));
				// Creating new user
				authRepo.save(newUser);
				logger.info("Created User account in database");

				// Generating token for the new user
				URI tokenUrl = tokenService.constructTokenUrl(tokenService.generateTokenForUser(newUser), "activate");
				logger.info("Generated token");

				// Sending email to the new user with the token for more authentication
				Mail mailObj = tokenService.constructMailBody(newUser, tokenUrl);
				emailService.sendEmailMessage(mailObj, new Request(), new SendGrid(apiKey));
				logger.info("Mail sent");

				return true;
			} else {
				logger.info("Unable to save user account on registering");
				throw new EntityFoundException("Email Id exists.Cannot create duplicate entity.");
			}
		} catch (DataIntegrityViolationException e) {
			logger.info("Catching Exception " + e.getMessage());
			throw new EntityFoundException(e.getMessage());
		}

	}

}
