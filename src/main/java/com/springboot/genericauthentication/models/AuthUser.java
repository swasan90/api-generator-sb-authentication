/**
 * Class to define the user model
 */
package com.springboot.genericauthentication.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.genericauthentication.validators.PasswordMatchConstraint;

import lombok.Data;
import lombok.ToString;

/**
 * @author swathy
 *
 */


@Entity
@Data
@Table(name="user")
@PasswordMatchConstraint.List({
	@PasswordMatchConstraint(field ="password",fieldMatch="confirmPassword",message="Passwords do not match!")
}) 
@ToString(exclude="tokens")
public class AuthUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@NotBlank(message="First Name cannot be blank")
	private String firstName;
	
	@NotBlank(message ="Last Name cannot be blank")
	private String lastName;
	
	@Email(message = "Please provide a valid Email")
	@NotBlank(message="Email cannot be blank")
	private String email;
	
	@NotBlank(message="Password cannot be blank")	 
	private String password;
	
	@NotBlank(message="Confirm Password cannot be blank")	 
	private String confirmPassword;
	
	private boolean enabled;	
 
	 
	private String uuid;
	
	private boolean status;
	
	@JsonIgnore
	private Date createdAt;
	
	@JsonIgnore
	private Date updatedAt; 
	
	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}
	
	@PreUpdate
	protected void OnUpdate() {
		updatedAt = new Date();
	}	
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="user" ,cascade=CascadeType.ALL)
	private List<UserToken> tokens = new ArrayList<>(); 
	
	public AuthUser() {
		
	} 
	 
	
	public AuthUser(String email,String password) {
		super();
		this.email = email;
		this.password = password;
	}
	 
	public AuthUser(String firstName,String lastName,String email,String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email; 
		this.password = password;
		this.confirmPassword = password;
		this.enabled = false;
	 	this.uuid = String.valueOf(UUID.randomUUID());
		this.status = false;		
				
	}
	
	
	
 
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="security_question_id")
//	private SecurityQuestion securityQuestion;
//	
//	@NotBlank(message="Security Answer is required")
//	private String answer;

}
