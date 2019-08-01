/**
 * Class to implement JWT authentication filter 
 */
package com.springboot.genericauthentication.jwt;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.springboot.genericauthentication.jwt.SecurityConstants.EXPIRATION_TIME;
import static com.springboot.genericauthentication.jwt.SecurityConstants.HEADER_STRING;
import static com.springboot.genericauthentication.jwt.SecurityConstants.SECRET;
import static com.springboot.genericauthentication.jwt.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
 

/**
 * @author swathy
 *
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticationRepository authRepo;
	
//	@Autowired
//	private RedisRepository redisRepo;
	

	public JWTAuthenticationFilter(AuthenticationManager authManager,ApplicationContext ctx) {
		this.authenticationManager = authManager;
		this.authRepo = ctx.getBean(AuthenticationRepository.class);
	}

	/**
	 * Function to attempt the authentication and returns the authenticated object.
	 * 
	 * @param HttpServletRequest, HttpServletResponse
	 * @return authentication
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			AuthUser creds = new ObjectMapper().readValue(req.getInputStream(), AuthUser.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {			
			throw new RuntimeException(e);
		}

	}

	/**
	 * Function to create token for the user upon successful authentication and adds
	 * the token in response header along with token prefix.
	 * 
	 * @param HttpServletRequest, HttpServletResponse,FilterChain,Authentication
	 * 
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		AuthUser user = authRepo.findByEmail(auth.getName());		
		String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
				.withClaim("given_name", user.getFirstName()).withClaim("family_name", user.getLastName())
				.withClaim("email_verified", user.isEnabled()).withClaim("uuid",user.getUuid()).withClaim("status", user.isStatus())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(HMAC512(SECRET.getBytes()));
		res.addHeader("access-control-expose-headers", "Authorization");
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
		//this.redisRepo.save(new JwtToken(UUID.fromString(user.getUuid()),token));
	}

}
