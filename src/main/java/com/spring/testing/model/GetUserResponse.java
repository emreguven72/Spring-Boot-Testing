package com.spring.testing.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserResponse {
	
	private int id;
	private String username;
	
}
