package com.spring.testing.factory;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.spring.testing.annotation.WithMockUser;
import com.spring.testing.security.UserPrincipal;
import com.spring.testing.security.UserPrincipalAuthenticationToken;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockUser annotation) {
		var authorities = Arrays.stream(annotation.authorities())
				.map(SimpleGrantedAuthority::new)
				.toList();
		
		var principal = UserPrincipal.builder()
				.id(annotation.id())
				.username("fakeUsername")
				.authorities(authorities)
				.build();
		
		var context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(new UserPrincipalAuthenticationToken(principal));
		
		return context;
	}

}
