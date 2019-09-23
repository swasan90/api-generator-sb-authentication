/**
 * Class to implement user details service interface methods
 */
package com.springboot.genericauthentication.user.service;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.repository.AuthenticationRepository;

/**
 * @author swathy
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private AuthenticationRepository authRepo;

	public UserDetailsServiceImpl(AuthenticationRepository authRepo) {
		this.authRepo = authRepo;
	}

	/**
	 * Function to validate if the user exist
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AuthUser user = authRepo.findByEmail(email);

		if (user != null && user.isEnabled()) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true,
					true, true, true, emptyList());
		}
		throw new UsernameNotFoundException("No user found with this email " + email);
	}

}
