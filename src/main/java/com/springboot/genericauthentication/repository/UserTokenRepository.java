/**
 * Interface to declare the user token methods.
 */
package com.springboot.genericauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.genericauthentication.models.UserToken;

/**
 * @author swathy
 *
 */
@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
	
	//Function declaration to find usertoken object by token
	UserToken findByToken(String token);

}
