/**
 * Class to define the configuration methods
 */
package com.springboot.genericauthentication.securityConfig;

import static com.springboot.genericauthentication.jwt.SecurityConstants.ACTIVATE_USER;
import static com.springboot.genericauthentication.jwt.SecurityConstants.DELETE_TOKEN;
import static com.springboot.genericauthentication.jwt.SecurityConstants.FORGOT_PASSWORD_URL;
import static com.springboot.genericauthentication.jwt.SecurityConstants.GET_JWT_TOKEN_BY_ID;
import static com.springboot.genericauthentication.jwt.SecurityConstants.List_All_Tokens;
import static com.springboot.genericauthentication.jwt.SecurityConstants.RESET_PASSWORD_URL_PATH;
import static com.springboot.genericauthentication.jwt.SecurityConstants.SIGN_UP_URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.springboot.genericauthentication.jwt.JWTAuthenticationFilter;
import com.springboot.genericauthentication.jwt.JWTAuthorizationFilter;
import com.springboot.genericauthentication.user.service.UserDetailsServiceImpl;

/**
 * @author swathy
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsServiceImpl userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${spring.allowed.origin.auth}")
	private String authclientHost;

	@Value("${spring.allowed.origin.apigen}")
	private String apigenclientHost;

	public SpringSecurityConfig(UserDetailsServiceImpl userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDetailsService = userDetailsService;

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	/**
	 * Function to implement the rules to allow the http url's.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/").permitAll()
				.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll().antMatchers(HttpMethod.GET, ACTIVATE_USER)
				.permitAll().antMatchers(HttpMethod.POST, FORGOT_PASSWORD_URL).permitAll()
				.antMatchers(HttpMethod.GET, RESET_PASSWORD_URL_PATH).permitAll()
				.antMatchers(HttpMethod.POST, RESET_PASSWORD_URL_PATH).permitAll()
				.antMatchers(HttpMethod.GET, GET_JWT_TOKEN_BY_ID).permitAll()
				.antMatchers(HttpMethod.GET, List_All_Tokens).permitAll().antMatchers(HttpMethod.DELETE, DELETE_TOKEN)
				.permitAll().anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(), getApplicationContext()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager())).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().frameOptions().disable();

	}

	/**
	 * Function to define the cors filter setting.
	 * 
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin(authclientHost);
		config.addAllowedOrigin(apigenclientHost);
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);

	}

}
