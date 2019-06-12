/**
 * Class to define the user model
 */
package com.springboot.genericauthentication.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

import com.springboot.genericauthentication.validators.PasswordMatchConstraint;

import lombok.Data;

/**
 * @author swathy
 *
 */

@Data
@Entity

@PasswordMatchConstraint.List({
	@PasswordMatchConstraint(field ="password",fieldMatch="confirmPassword",message="Passwords do not match!")
}) 

public class User {
	
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
	
	//@CreatedDate 
	private Date createdAt;
	
	//@LastModifiedDate
	private Date updatedAt; 
	
	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}
	
	@PreUpdate
	protected void OnUpdate() {
		updatedAt = new Date();
	}	
	
	@OneToMany(mappedBy="user" ,cascade=CascadeType.ALL,orphanRemoval=true)
	private List<UserToken> tokens = new ArrayList<>(); 
	
	public User() {
		
	} 
	 
	public User(String firstName,String lastName,String email,String password) {
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
