/**
 * Interface to implement password match constraint
 */
package com.springboot.genericauthentication.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author swathy
 *
 */
@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatchConstraint {
	String message() default "Invalid password";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String field();

	String fieldMatch();

	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {
		PasswordMatchConstraint[] value();
	}
}
