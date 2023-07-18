package com.spring.testing.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.testing.model.CreateUserRequest;
import com.spring.testing.model.LoginRequest;
import com.spring.testing.model.LoginResponse;
import com.spring.testing.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/authenticate")
	public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
		return authService.attemptLogin(loginRequest);
	}
	
	@PostMapping("/register")
	public void save(@RequestBody @Validated CreateUserRequest request) {
		authService.register(request);
	}
	
}
