/**
 * 
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
public interface UserTokenRepository extends JpaRepository<UserToken,Long> {
	
	UserToken findByToken(String token);

}
