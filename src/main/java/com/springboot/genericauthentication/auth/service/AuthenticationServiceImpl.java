/**
 * Class to implement authentication service methods.
 */
package com.springboot.genericauthentication.auth.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.springboot.genericauthentication.email.EmailService;
import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.models.UserToken;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
import com.springboot.genericauthentication.repository.UserTokenRepository;

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
	private UserTokenRepository userTokenRepo;

	@Autowired
	private EmailService emailService;

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
				URI tokenUrl = generateTokenForNewUser(newUser);
				logger.info("Generated token");

				// Sending email to the new user with the token for more authentication
				Mail mailObj = constructMailBody(newUser, tokenUrl);
				Request request = new Request();
				SendGrid sg = new SendGrid(apiKey);
				emailService.sendEmailMessage(mailObj, request, sg);
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

	/**
	 * Function to generate token for the user
	 * 
	 * @param user
	 * @return String
	 */
	private URI generateTokenForNewUser(AuthUser user) {
		String token = String.valueOf(UUID.randomUUID()).replace("-", "");
		Instant expirationDate = Instant.now().plus(Duration.ofHours(24));
		UserToken userToken = new UserToken(token, user, expirationDate);
		userTokenRepo.save(userToken);
		return constructTokenUrl(token);
	}

	/**
	 * Function to construct url with token for the new user
	 * 
	 * @param token
	 * @return String
	 * @throws MalformedURLException
	 */
	private URI constructTokenUrl(String token) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().queryParam("token", token).build().toUri();
		return uri;
	}

	/**
	 * Function to construct mail object
	 * 
	 * @param user
	 * @param tokenUrl
	 * @return Mail
	 */
	private Mail constructMailBody(AuthUser user, URI tokenUrl) {
		String subject = "Activate your account on your registration";
		String body = " Welcome " + user.getFirstName()
				+ ",<br/><p>You are receiving this email because you have registered in our site.\n\n Please click on the below link to activate your account.<br/>"
				+ tokenUrl
				+ "<p>Kindly note that this link will be activated only for 24 hours from now.<br/><br/><br/>Regards,<br/> Admin Team</p>";
		Content content = new Content("text/html", body);
		return new Mail(from, subject, new Email(user.getEmail()), content);
	}

}
