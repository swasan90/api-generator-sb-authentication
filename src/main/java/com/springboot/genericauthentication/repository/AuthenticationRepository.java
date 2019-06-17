/**
 * Interface to define authentication methods
 */
package com.springboot.genericauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.genericauthentication.models.AuthUser;

/**
 * @author swathy
 *
 */
@Repository
public interface AuthenticationRepository extends JpaRepository<AuthUser, Long> {

	//Function declaration to find user by email
	AuthUser findByEmail(String email);
	
	//Function declaration to find user by id
	AuthUser findById(long id);
}
