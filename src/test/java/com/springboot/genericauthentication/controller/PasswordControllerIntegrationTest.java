package com.springboot.genericauthentication.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.genericauthentication.models.AuthUser;

@ExtendWith(MockitoExtension.class) 
@WebAppConfiguration
@SpringBootTest 
@ContextConfiguration  
class PasswordControllerIntegrationTest {
	
 
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	AuthUser user;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		this.user = new AuthUser("jim.carry@example.com","password");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	/**
	 * Function to test forgot password that returns error
	 * @throws Exception
	 */
	@DisplayName("Testing forgot password returns error")
	@Test
	public void testForgotPasswordReturnsError() throws Exception {
		this.mockMvc.perform(post("/forgotPassword").contentType("application/json").content(new ObjectMapper().writeValueAsString(this.user)))
							  .andDo(print())
							  .andExpect(status().isUnauthorized())
							  .andExpect(jsonPath("$.message")
							  .value("Email id does not exist (or) you may have not activated your account"))
							  .andExpect(jsonPath("$.status").value(false))
							  .andReturn();
	}
	
	/**
	 * Function to test forgot password that sends the token link to the user's email id
	 * @throws Exception
	 */
	@DisplayName("Testing forgot password returns succcess")
	@Test
	public void testForgotPasswordReturnsSuccess() throws Exception {
		AuthUser user = new AuthUser("swasan90@gmail.com","password");
		this.mockMvc.perform(post("/forgotPassword").contentType("application/json").content(new ObjectMapper().writeValueAsString(user)))
							  .andDo(print())
							  .andExpect(status().isOk())
							  .andExpect(jsonPath("$.message")
							  .value("Email has been sent to your given email id for resetting password"))	
							  .andExpect(jsonPath("$.status").value(true))
							  .andReturn();
	}
	
	/**
	 * Function to test reset password that returns error.
	 * @throws Exception
	 */
	@DisplayName("Testing reset password returns error")
	@Test
	public void testResetPasswordReturnsError() throws Exception {
		this.mockMvc.perform(post("/reset").contentType("application/json").content(new ObjectMapper().writeValueAsString(this.user)))
					.andDo(print())
					.andExpect(status().isForbidden())
					.andExpect(jsonPath("$.message").value("Unable to reset your password."))
					.andExpect(jsonPath("$.status").value(false))
					.andReturn();
					
			
	}
	
	/**
	 * Function to test reset password that resets the user's password.
	 * @throws Exception
	 */
	@DisplayName("Testing reset password that resets the user's password")
	@Test
	public void testResetPassword() throws Exception{
		AuthUser user = new AuthUser("swasan90@gmail.com","password");
		this.mockMvc.perform(post("/reset").contentType("application/json").content(new ObjectMapper().writeValueAsString(user))
					.param("token", "8b74d644e6a24e0ebe939bdbf396a040"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.message").value("Your password has been reset.Kindly login with your new password"))
					.andExpect(jsonPath("$.status").value(true))
					.andReturn();

	}
	
	/**
	 * Function to test validate password link
	 * @throws Exception
	 */
	@DisplayName("Testing validate password link")
	@Test
	public void testValidatePasswordLink() throws Exception {
		this.mockMvc.perform(get("/reset").contentType("application/json").param("token", "8b74d644e6a24e0ebe939bdbf396a040"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value(true))
					.andReturn();
		
	}
	
	/**
	 * Function to test validate password link that returns false.
	 * @throws Exception
	 */
	@DisplayName("Testing validate password link returns false")
	@Test
	public void testValidatePasswordLinkReturnsFalse() throws Exception{
		this.mockMvc.perform(get("/reset").contentType("application/json").param("token", ""))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value(false))
					.andReturn();
	}

}
