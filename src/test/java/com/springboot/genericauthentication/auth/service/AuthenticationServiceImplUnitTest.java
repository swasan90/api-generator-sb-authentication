package com.springboot.genericauthentication.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.genericauthentication.email.EmailService;
import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.exception.MailErrorException;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
import com.springboot.genericauthentication.token.service.TokenService;

@ExtendWith(MockitoExtension.class) 
class AuthenticationServiceImplUnitTest {

	AuthUser user;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private EmailService emailService;

	@Mock
	private TokenService tokenService;

	@Mock
	private AuthenticationRepository authRepo;

	@InjectMocks
	private AuthenticationService authService = new AuthenticationServiceImpl(this.bCryptPasswordEncoder);

	@BeforeEach
	void setUp() throws Exception {
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.user = new AuthUser("Jim", "Carry", "jim.carry@example.com", "password");
	}

	@AfterEach
	void tearDown() throws Exception {
		this.bCryptPasswordEncoder =null;
		this.user = null;
	}
	
	/**
	 * Function to test user registration process to return true.
	 * @throws EntityFoundException
	 * @throws IOException
	 * @throws MailErrorException
	 */
	@Test
	public void testRegisterUserReturnsTrue() throws EntityFoundException, IOException, MailErrorException {
		Mockito.when(authRepo.findByEmail("jim.carry@example.com")).thenReturn(null);
		boolean expected = authService.registerUser(this.user);
		assertEquals(expected, true);
	}
	
	/**
	 * Function to test if the user registration process gets failed.
	 * @throws EntityFoundException
	 * @throws IOException
	 * @throws MailErrorException
	 */
	@Test
	public void testRegisterUserReturnsEntityFoundException()
			throws EntityFoundException, IOException, MailErrorException {		
		Mockito.when(authRepo.findByEmail("jim.carry@example.com")).thenReturn(this.user);
		assertThrows(EntityFoundException.class,()->{
			authService.registerUser(this.user);
		},"Email Id exists.Cannot create duplicate entity.");
	}
	 
}
