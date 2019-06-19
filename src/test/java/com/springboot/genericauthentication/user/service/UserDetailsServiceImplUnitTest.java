package com.springboot.genericauthentication.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springboot.genericauthentication.exception.EntityFoundException;
import com.springboot.genericauthentication.models.AuthUser;
import com.springboot.genericauthentication.repository.AuthenticationRepository;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplUnitTest {
	
	@Mock
	private AuthenticationRepository authRepo;
	
	@InjectMocks
	private UserDetailsService userDetailService = new UserDetailsServiceImpl(this.authRepo);
	
	AuthUser user =null;
	
	@BeforeEach
	void setUp() throws Exception {
		user = new AuthUser("jim.carry@example.com","password");
		user.setEnabled(true);
	}

	@AfterEach
	void tearDown() throws Exception {
		user =null;
	}

	 /**
	  * Function to test to retrieve user by email/username.
	  */
	 @DisplayName("Retrieve user by email")
	 @Test
	 public void testLoadUserByUsername() throws UsernameNotFoundException {
		 Mockito.when(authRepo.findByEmail(this.user.getEmail())).thenReturn(this.user);
		 UserDetails user = userDetailService.loadUserByUsername(this.user.getEmail());
		 assertEquals(user.getUsername(),this.user.getEmail());
		 assertTrue(user.isEnabled());
		 assertTrue(user.isAccountNonExpired());
		 assertTrue(user.isCredentialsNonExpired());
	 }
	 
	 /**
	  * Function to test load user by username throws UsernameNotFoundException
	  * @throws UsernameNotFoundException
	  */
	 @DisplayName("Load User by username /email throws UsernameNotFoundException")
	 @Test
	 public void testLoadUserByUsernameReturnsError() throws UsernameNotFoundException{
		 Mockito.when(authRepo.findByEmail(this.user.getEmail())).thenReturn(null);		 
		 assertThrows(UsernameNotFoundException.class,()->{
			 userDetailService.loadUserByUsername(this.user.getEmail());
			},"No user found with this email "+this.user.getEmail());
	 }
	 

}
