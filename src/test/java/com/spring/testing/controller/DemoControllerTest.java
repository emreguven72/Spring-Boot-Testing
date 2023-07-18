package com.spring.testing.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.testing.annotation.WithAdminUser;
import com.spring.testing.annotation.WithMockUser;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoControllerTest {
	
	@Autowired
	private MockMvc api;
	
	private final String baseUrl = "/api/v1/demo";

	@Test
	void anyoneCanViewPublicEndpoint() throws Exception {
		api.perform(get(baseUrl))
			.andExpect(status().isOk())
			.andExpect(content().string("Public Message"));
	}
	
	@Test
	void notLoggedIn_shouldNotSeeSecuredEndpoint() throws Exception {
		api.perform(get(baseUrl + "/secured"))
			.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	@WithMockUser
	void loggedIn_shouldSeeSecuredEndpoint() throws Exception {
		api.perform(get(baseUrl + "/secured"))
			.andExpect(status().isOk())
			.andExpect(content().string("If you see this message then you are logged in as fakeUsername"));
		
	}
	
	@Test
	void notLoggedIn_shouldNotSeeAdminEndpoint() throws Exception {
		api.perform(get(baseUrl + "/admin"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	void simpleUser_shouldNotSeeAdminEndpoint() throws Exception {
		api.perform(get(baseUrl + "/admin"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithAdminUser
	void admin_shouldSeeAdminEndpoint() throws Exception {
		api.perform(get(baseUrl + "/admin"))
			.andExpect(status().isOk())
			.andExpect(content().string("If you see this message then you are admin"));
	}
	
}
