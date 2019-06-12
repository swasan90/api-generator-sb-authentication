/**
 * 
 */
package com.springboot.genericauthentication.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

/**
 * @author swathy
 *
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatchConstraint,Object> {
	
	private String password;
	
	private String passwordMatch;
	
	
	@Override
	public void initialize(PasswordMatchConstraint constraintAnnotation) {
		this.password = constraintAnnotation.field();
		this.passwordMatch = constraintAnnotation.fieldMatch();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
		Object passwordMatchValue = new BeanWrapperImpl(value).getPropertyValue(passwordMatch);    
		
		
		if(passwordValue != null) {				 
			return passwordValue.equals(passwordMatchValue);
		}else {						 
			return passwordValue == null;
		}  
	}

}
