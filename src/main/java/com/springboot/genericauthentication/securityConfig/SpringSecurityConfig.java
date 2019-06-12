/**
 * 
 */
package com.springboot.genericauthentication.securityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author swathy
 *
 */
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
    {
		http
			.cors()
			.and()
			.csrf().disable()
			.authorizeRequests().antMatchers(HttpMethod.POST,"/login").permitAll()
			.antMatchers(HttpMethod.POST,"/register").permitAll()
			.antMatchers(HttpMethod.GET,"/register").permitAll()
			.anyRequest().authenticated().and()
			//.addFilter(new JWTAuthenticationFilter(authenticationManager()))
	        //.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.headers().frameOptions().disable();	
		
    }

}
