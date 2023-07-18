package com.spring.testing.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.spring.testing.entity.Token;
import com.spring.testing.entity.TokenType;
import com.spring.testing.entity.User;
import com.spring.testing.model.CreateUserRequest;
import com.spring.testing.model.LoginRequest;
import com.spring.testing.model.LoginResponse;
import com.spring.testing.security.JwtService;
import com.spring.testing.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtService jwtService;
	private final UserService userService;
	private final TokenService tokenService;
	private final AuthenticationManager authenticationManager;
	
	public LoginResponse attemptLogin(LoginRequest loginRequest) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
		
		var roles = principal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		
		String jwt = jwtService.create(principal.getId(), principal.getUsername(), roles);
		
		User authUser = userService.findByUsername(principal.getUsername()).orElseThrow();
		
		revokeAllUserTokens(authUser);
		createNewTokenForUser(authUser, jwt);
		
		return LoginResponse.builder()
				.token(jwt)
				.build();
	}
	
	public void register(CreateUserRequest request) {
		User user = userService.save(request).orElseThrow();
		
		String jwt = jwtService.create(user.getId(), user.getUsername(), List.of(String.valueOf(user.getRole())));
		
		createNewTokenForUser(user, jwt);		
	}
	
	private void createNewTokenForUser(User user, String jwt) {
		Token token = Token.builder()
				.token(jwt)
				.type(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.user(user)
				.build();
		
		tokenService.save(token);
	}
	
	private void revokeAllUserTokens(User user) {
		var validTokens = tokenService.findAllValidTokensByUser(user.getId());
		
		if(validTokens.isEmpty()) {
			return;
		}
		
		validTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
			tokenService.save(token);
		});
	}

}
