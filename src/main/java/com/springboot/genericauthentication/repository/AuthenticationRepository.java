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
public interface AuthenticationRepository extends JpaRepository<AuthUser,Long> {
	
	AuthUser findByEmail(String email);
	
	AuthUser findById(long id);
}
