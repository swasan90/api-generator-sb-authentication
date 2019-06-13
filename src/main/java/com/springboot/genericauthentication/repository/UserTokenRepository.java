/**
 * 
 */
package com.springboot.genericauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.genericauthentication.models.User;
import com.springboot.genericauthentication.models.UserToken;

/**
 * @author swathy
 *
 */
@Repository
public interface UserTokenRepository extends JpaRepository<UserToken,Long> {
	
	User findByToken(String token);

}
