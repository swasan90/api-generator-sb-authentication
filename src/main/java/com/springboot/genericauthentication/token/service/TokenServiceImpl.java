/**
 * 
 */
package com.springboot.genericauthentication.token.service; 

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.genericauthentication.models.AuthUser;
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

	@Override
	public boolean validateToken(String token) {
		if(token !=null) {
			UserToken userToken = userTokenRepo.findByToken(token);
			return checkTokenExpirationDate(userToken);			
		}
		return false;
	}
	
	/**
	 * Function to check token expiration date .
	 * @param userToken
	 * @return boolean
	 */
	private boolean checkTokenExpirationDate(UserToken userToken) {
		if(userToken !=null) {
			Instant current = Instant.now();			
			if(userToken.getExpirationDate().isAfter(current)) {
				AuthUser user  = authRepo.findById(userToken.getUser().getId());
				 if(user!=null) {
					 user.setEnabled(true);
					 user.setStatus(true);
					 authRepo.save(user);
					 return true;
				 }			
			}
		}
		return false;
		
	}
	

}
