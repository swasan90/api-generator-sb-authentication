/**
 * Class to define token model for the user.
 */
package com.springboot.genericauthentication.models;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/**
 * @author swathy
 *
 */
@Entity
@Data
public class UserToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	private String token;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private AuthUser user;

	private Instant expirationDate;

	public UserToken() {
	};

	public UserToken(String token, AuthUser user, Instant expDate) {
		super();
		this.token = token;
		this.user = user;
		this.expirationDate = expDate;
	}

}
