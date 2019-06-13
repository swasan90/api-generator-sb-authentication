/**
 * Class to implement authentication service methods.
 */
package com.springboot.genericauthentication.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.genericauthentication.email.EmailService;
import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.MailObject;
import com.springboot.genericauthentication.models.User;
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
	
	public AuthenticationServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	@Transactional
	public boolean registerUser(User user) throws EntityFoundException, MailErrorException{
		User usr = authRepo.findByEmail(user.getEmail());
		try {
			if(usr ==null) {
				User newUser = new User(user.getFirstName(),user.getLastName(),user.getEmail(),bCryptPasswordEncoder.encode(user.getPassword()));
				// Creating new user
				authRepo.save(newUser);
				logger.info("Created User account in database");
				
				//Generating token for the new user
				String tokenUrl = generateTokenForNewUser(newUser);
				logger.info("Generated token");
				
				//Sending email to the new user with the token for more authentication
				MailObject mailObj = constructMailBody(newUser,tokenUrl);
				emailService.sendEmailMessage(mailObj.getTo(), mailObj.getSubject(), mailObj.getMessage());
				
				return true;
			}else {
				logger.info("Unable to save user account on registering" );
				throw new EntityFoundException("Email Id exists.Cannot create duplicate entity.");
			}
		}catch(DataIntegrityViolationException e) {
			logger.info("Catching Exception "+e.getMessage());
			throw new EntityFoundException(e.getMessage());
		} 
		 
	}
	
	private String generateTokenForNewUser(User user) {
		String token = String.valueOf(UUID.randomUUID()).replace("-", "");
		Instant expirationDate = Instant.now().plus(Duration.ofHours(24));		
		UserToken userToken = new UserToken(token,user,Date.from(expirationDate));
		userTokenRepo.save(userToken);
		return constructTokenUrl(token);
	}
	
	private String constructTokenUrl(String token) {
		final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		StringBuffer sb = new StringBuffer();
		sb.append(baseUrl).append("token=").append(token);
		return sb.toString();		
	}
	
	private MailObject constructMailBody(User user,String tokenUrl) {
		String subject = "Activate your account on your registration";
		String body = " Welcome"+user.getFirstName()+",\n\n You are receiving this email because you have registered in our site.\n Please click on the below link to activate your account.\n"+tokenUrl+"\n\nKindly note that this link will be activated only for 24 hours from now.";
		return new MailObject(user.getEmail(),subject,body);		
	 
	}

	 
} 
