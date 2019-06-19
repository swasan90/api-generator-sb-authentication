/**
 * Class to implement token service methods
 */
package com.springboot.genericauthentication.token.service;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.models.MailObject;
import com.springboot.genericauthentication.models.UserToken;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
import com.springboot.genericauthentication.repository.UserTokenRepository;

/**
 * @author swathy
 *
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

	@Autowired
	private UserTokenRepository userTokenRepo;

	@Autowired
	private AuthenticationRepository authRepo;

	@Value("${sendgrid.api.key}")
	private String apiKey;

	@Value("${sendgrid.from.mail}")
	private Email from;

	/***
	 * Function to validate user's token.
	 */
	@Override
	public boolean validateToken(String token) {
		if (token != null) {
			UserToken userToken = userTokenRepo.findByToken(token);
			return checkTokenExpirationDate(userToken);
		}
		return false;
	}

	/**
	 * Function to check token expiration date .
	 * 
	 * @param userToken
	 * @return boolean
	 */
	private boolean checkTokenExpirationDate(UserToken userToken) {
		if (userToken != null) {
			Instant current = Instant.now();
			if (userToken.getExpirationDate().isAfter(current)) {
				AuthUser user = authRepo.findById(userToken.getUser().getId());			 
				if (user != null && !user.isEnabled()) {					 
					activateUser(user);					
				}	
				return true;
			}
		}
		return false;

	}

	/**
	 * Function to activate the user upon registration
	 * 
	 * @param user
	 */
	private void activateUser(AuthUser user) {
		user.setEnabled(true);
		user.setStatus(true);
		authRepo.save(user);
	}

	/**
	 * Function to reset the user's password
	 * 
	 * @pa
	 */

	/**
	 * Function to generate token for the user
	 * 
	 * @param user
	 * @return String
	 */
	@Override
	public String generateTokenForUser(AuthUser user) {
		String token = String.valueOf(UUID.randomUUID()).replace("-", "");
		Instant expirationDate = Instant.now().plus(Duration.ofHours(24));
		UserToken userToken = new UserToken(token, user, expirationDate);
		userTokenRepo.save(userToken);
		return token;
	}

	/**
	 * Function to construct url with token for the new user
	 * 
	 * @param token
	 * @return URI
	 */
	@Override
	public URI constructTokenUrl(String token, String action) {
		URI uri = null;
		if (action.equals("activate")) {
			uri = ServletUriComponentsBuilder.fromCurrentContextPath().port(8080).path(action).queryParam("token", token).build()
					.toUri();
		} else if (action.equals("reset")) {
			uri = ServletUriComponentsBuilder.fromCurrentContextPath().port(8080).path(action).queryParam("token", token).build()
					.toUri();
		
		}
		return uri;
	}

	/**
	 * Function to construct mail object
	 * 
	 * @param user
	 * @param tokenUrl
	 * @return Mail
	 */
	@Override
	public Mail constructMailBody(AuthUser user, URI tokenUrl) {
		MailObject mailObj = new MailObject();
		if (user.isEnabled()) {
			mailObj.setSubject("Did you forgot your password?");
			mailObj.setGreeting("Welcome " + user.getFirstName());
			mailObj.setMessage(
					",<br/><p>You are receiving this email because you have clicked forgot password.\n\n Please click on the below link to reset your password.<br/>"
							+ tokenUrl
							+ "<p>Kindly note that this link will be activated only for 24 hours from now.<br/><br/><br/>Regards,<br/> Admin Team</p>");

		} else {			
			mailObj.setSubject("Activate your account on your registration");
			mailObj.setGreeting("Welcome " + user.getFirstName());
			mailObj.setMessage(
					",<br/><p>You are receiving this email because you have registered in our site.\n\n Please click on the below link to activate your account.<br/>"
							+ tokenUrl
							+ "<p>Kindly note that this link will be activated only for 24 hours from now.<br/><br/><br/>Regards,<br/> Admin Team</p>");
		}
		Content content = new Content("text/html", mailObj.getGreeting() + mailObj.getMessage());
		return new Mail(from, mailObj.getSubject(), new Email(user.getEmail()), content);
	}

}
