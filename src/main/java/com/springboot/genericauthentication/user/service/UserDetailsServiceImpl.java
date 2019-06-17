/**
 * 
 */
package com.springboot.genericauthentication.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.genericauthentication.models.User;
import com.springboot.genericauthentication.repository.AuthenticationRepository;
import static java.util.Collections.emptyList;

/**
 * @author swathy
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private AuthenticationRepository authRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = authRepo.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		 
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),emptyList());
	}
	

}
 