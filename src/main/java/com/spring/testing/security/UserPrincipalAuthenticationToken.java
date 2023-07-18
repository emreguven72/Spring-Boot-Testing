package com.spring.testing.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {
	private final UserPrincipal userPrincipal;
	
	public UserPrincipalAuthenticationToken(UserPrincipal userPrincipal) {
		super(userPrincipal.getAuthorities());
		setAuthenticated(true);
		this.userPrincipal = userPrincipal;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return userPrincipal;
	}

}
