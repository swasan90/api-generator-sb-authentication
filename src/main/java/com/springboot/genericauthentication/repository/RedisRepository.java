/**
 * 
 */
package com.springboot.genericauthentication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.genericauthentication.models.JwtToken;

/**
 * @author swathy
 *
 */
@Repository
public interface RedisRepository extends CrudRepository<JwtToken, String>{

}
