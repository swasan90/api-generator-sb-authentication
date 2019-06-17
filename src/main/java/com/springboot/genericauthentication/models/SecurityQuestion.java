/**
 * Class to define the security question 
 */
package com.springboot.genericauthentication.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author swathy
 *
 */

@Data
@Entity
public class SecurityQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String securityQuestion;

}
