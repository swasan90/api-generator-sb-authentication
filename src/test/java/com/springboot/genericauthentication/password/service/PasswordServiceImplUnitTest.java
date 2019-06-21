package com.springboot.genericauthentication.password.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.genericauthentication.email.EmailService;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.models.ResetPassword;
import com.springboot.genericauthentication.models.UserToken;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
import com.springboot.genericauthentication.repository.UserTokenRepository;
import com.springboot.genericauthentication.token.service.TokenService;

@ExtendWith(MockitoExtension.class)
class PasswordServiceImplUnitTest {

	@Mock
	private AuthenticationRepository authRepo;
	
	@Mock
	private UserTokenRepository userTokenRepo;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Mock
	private TokenService tokenService;
	
	@Mock
	private EmailService emailService;
	
	AuthUser user;
	
	@InjectMocks
	private PasswordService passwordService = new PasswordServiceImpl(this.bCryptPasswordEncoder);
	
	@BeforeEach
	void setUp() throws Exception {
		user = new AuthUser("jim.carry@example.com",this.bCryptPasswordEncoder.encode("password"));		
		user.setConfirmPassword(user.getPassword());
		user.setEnabled(true);
	}

	@AfterEach
	void tearDown() throws Exception {
		user = null;
	}
	/**
	 * Function to test forgot password
	 * @throws MailErrorException
	 * @throws IOException
	 */
	@DisplayName("Testing forgot password that returns true")
	@Test
	void testForgotPassword() throws MailErrorException, IOException {		 
		 Mockito.when(authRepo.findByEmail(user.getEmail())).thenReturn(user);		 
		 boolean expected = passwordService.forgotPassword(user.getEmail());
		 assertEquals(expected,true);
	}
	
	/**
	 * Function to test forgot password that fails.
	 * @throws MailErrorException
	 * @throws IOException
	 */
	@DisplayName("Testing forgot password that returns false")
	@Test
	void testForgotPasswordReturnsFalse() throws MailErrorException, IOException {		
		 Mockito.when(authRepo.findByEmail(user.getEmail())).thenReturn(null);		 
		 boolean expected = passwordService.forgotPassword(user.getEmail());
		 assertEquals(expected,false);
	}
	
	
	/**
	 * Function to test reset password
	 * @throws IOException
	 */
	@DisplayName("Test function to reset password")
	@Test
	void testResetPassword() throws IOException {
		
		String token  ="f8ed96fb26374af4bf701fed3c3f58f9";			 
		ResetPassword resetpwd = new ResetPassword(token,"password");
		UserToken userToken  = new UserToken(token,user,Instant.now().plus(Duration.ofHours(24))); 
		
		Mockito.when(userTokenRepo.findByToken(token)).thenReturn(userToken);
		boolean expected = passwordService.resetPassword(resetpwd);	
		 
		assertEquals(expected,true);
		assertNotEquals("password",user.getPassword());
		assertEquals(user.getPassword(),user.getConfirmPassword());
	}
	
	/**
	 * Function to test reset password that returns false
	 * @throws IOException
	 */
	@DisplayName("Test function to reset password returns false")
	@Test
	public void testResetPasswordReturnsFalse() throws IOException {
		String token  ="f8ed96fb26374af4bf701fed3c3f58f7";	  
		Mockito.when(userTokenRepo.findByToken(token)).thenReturn(null);
		boolean expected = passwordService.resetPassword(new ResetPassword(token,"password"));
		assertEquals(expected,false);
	}

}
