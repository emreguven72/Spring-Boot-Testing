package com.spring.testing.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtToUserPrincipalConverter {
	
	public UserPrincipal convert(DecodedJWT jwt) {
		return UserPrincipal.builder()
				.id(Integer.valueOf(jwt.getSubject()))
				.username(jwt.getClaim("username").asString())
				.authorities(extractAuthorities(jwt))
				.build();
	}
	
	private List<SimpleGrantedAuthority> extractAuthorities(DecodedJWT jwt) {
		var authorities = jwt.getClaim("authorities");
		
		if(authorities.isNull() || authorities.isMissing()) {
			return List.of();
		}
		
		return authorities.asList(SimpleGrantedAuthority.class);
	}
	
}
