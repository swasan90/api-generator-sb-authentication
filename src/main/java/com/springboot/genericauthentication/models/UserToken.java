/**
 * Class to define token model for the user.
 */
package com.springboot.genericauthentication.models;

 

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * @author swathy
 *
 */
@Entity
@Data
public class UserToken {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	private String token;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	private Date expirationDate;
	
	public UserToken(String token, User user,Date expDate) {
		this.token = token;
		this.user = user;
		this.expirationDate = expDate;
	}
	
	
}
