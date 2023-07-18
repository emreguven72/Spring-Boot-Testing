package com.spring.testing.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequest {

	private String username;
	private String password;
	
}