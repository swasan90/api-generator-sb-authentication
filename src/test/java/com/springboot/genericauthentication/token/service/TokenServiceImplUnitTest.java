package com.springboot.genericauthentication.token.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.models.UserToken;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
import com.springboot.genericauthentication.repository.UserTokenRepository;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplUnitTest {

	private String token;

	UserToken userToken = null;
	AuthUser user = null;

	@Mock
	private UserTokenRepository userTokenRepo;

	@Mock
	private AuthenticationRepository authRepo;

	@InjectMocks
	private TokenService tokenService = new TokenServiceImpl();

	@BeforeEach
	void setUp() throws Exception {
		this.token = "3c94c0741fb74ddd8db673723f04dff0";
		this.user = new AuthUser("jim.carry@example.com", "password");
		userToken = new UserToken(this.token, this.user, Instant.now().plus(Duration.ofHours(24)));

	}

	@AfterEach
	void tearDown() throws Exception {
		this.token = "";
		this.user = null;
		userToken = null;
	}
	
	/**
	 * Function to validate token returns true
	 */
	@DisplayName("Validating token that retuns true")
	@Test
	public void testValidateTokenReturnsTrue() {
		Mockito.when(userTokenRepo.findByToken(this.token)).thenReturn(this.userToken);
		boolean expected = tokenService.validateToken(this.token);
		assertEquals(expected, true);
	}
	
	/**
	 * Function to validate token that returns false.
	 */
	@DisplayName("Validating token that returns false")
	@Test
	public void testValidateTokenReturnsFalse() {
		Mockito.when(userTokenRepo.findByToken(this.token)).thenReturn(null);
		boolean expected = tokenService.validateToken(this.token);
		assertEquals(expected, false);
	}
	
	/**
	 * Function to validate expired token.
	 */
	@DisplayName("Validating expired token")
	@Test
	public void testValidateExpiredToken() {
		UserToken usrToken = new UserToken(this.token, this.user, Instant.now().minus(Duration.ofHours(24)));
		Mockito.when(userTokenRepo.findByToken(this.token)).thenReturn(usrToken);
		boolean expected = tokenService.validateToken(this.token);
		assertEquals(expected, false);
	}

	/**
	 * Function to test generate token.
	 */
	@DisplayName("Generating token")
	@Test
	public void testGeneratingTokenForUser() {
		Mockito.lenient().when(userTokenRepo.save(this.userToken)).thenReturn(this.userToken);
		String expectedToken = tokenService.generateTokenForUser(this.user);
		assertEquals(expectedToken.length(), this.token.length());
		assertNotEquals(expectedToken, this.token);
	}	

}
