package com.spring.testing.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.testing.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

	@GetMapping()
	public String publicMessage() {
		return "Public Message";
	}
	
	@GetMapping("/secured")
	public String securedMessage(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return "If you see this message then you are logged in as " + userPrincipal.getUsername();
	}
	
	@GetMapping("/admin")
	public String adminMessage(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return "If you see this message then you are admin";
	}
	
}
