/**
 * 
 */
package com.springboot.genericauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.genericauthentication.models.JwtToken;

/**
 * @author swathy
 *
 */
@Repository
public interface TokenRepository extends JpaRepository<JwtToken, String>{

}
