/**
 * 
 */
package com.springboot.genericauthentication.models;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author swathy
 *
 */
@Data
public class ResetPassword {
	
	@NotNull(message="token cannot be blank")
	private String token;
	
	@NotNull(message="password cannot be blank")
	private String password;
	
	public ResetPassword() {};
	
	public ResetPassword(String token,String password) {
		super();
		this.password =password;
		this.token =token;
	}
	

}
