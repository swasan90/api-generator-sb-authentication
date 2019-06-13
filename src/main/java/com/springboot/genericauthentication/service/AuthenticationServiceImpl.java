/**
 * Class to implement authentication service methods.
 */
package com.springboot.genericauthentication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.genericauthentication.controller.RegistrationController;
import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.models.User;
import com.springboot.genericauthentication.repository.AuthenticationRepository;

/**
 * @author swathy
 *
 */
@Service("authService")
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	private AuthenticationRepository authRepo;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public AuthenticationServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public boolean registerUser(User user) throws EntityFoundException{
		User usr = authRepo.findByEmail(user.getEmail());
		try {
			if(usr ==null) {
				User newUser = new User(user.getFirstName(),user.getLastName(),user.getEmail(),bCryptPasswordEncoder.encode(user.getPassword()));
				authRepo.save(newUser);
				logger.info("Created User account in database");
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

	 
} 
