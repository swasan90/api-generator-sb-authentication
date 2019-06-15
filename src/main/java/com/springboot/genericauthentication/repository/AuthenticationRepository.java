/**
 * Interface to define authentication methods
 */
package com.springboot.genericauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.genericauthentication.models.User;

/**
 * @author swathy
 *
 */
@Repository
public interface AuthenticationRepository extends JpaRepository<User,Long> {
	
	User findByEmail(String email);
	
	User findById(long id);
}
