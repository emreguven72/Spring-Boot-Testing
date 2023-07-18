package com.spring.testing.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtToUserPrincipalConverter jwtToUserPrincipalConverter;
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {		
		extractTokenFromRequest(request)
			.map(jwtService::checkIfJwtValid)
			.map(jwtService::decode)
			.map(jwtToUserPrincipalConverter::convert)
			.map(UserPrincipalAuthenticationToken::new)
			.ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
		
		filterChain.doFilter(request, response);
	}
	
	private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
		var header = request.getHeader("Authorization");
		
		if(!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
			return Optional.empty();
		}
		
		return Optional.of(header.substring(7));
	}
}
