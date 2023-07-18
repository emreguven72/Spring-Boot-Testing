package com.spring.testing.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.testing.service.LogoutService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomUserDetailsService customUserDetailsService;
	private final LogoutService logoutService;
	private final UnauthorizedHandler unauthorizedHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.cors().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().formLogin().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and()
			.securityMatcher("/**")
			.authorizeHttpRequests(registry -> registry
					.requestMatchers(
							"/api/v1/demo",
							"/api/v1/auth/authenticate",
							"/api/v1/auth/register"
					).permitAll()
					.requestMatchers("/api/v1/demo/admin").hasAuthority("ADMIN")
					.anyRequest().authenticated()
			).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.logout()
			.logoutUrl("/api/v1/auth/logout")
			.addLogoutHandler(logoutService)
			.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
			
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
					.userDetailsService(customUserDetailsService)
					.passwordEncoder(passwordEncoder())
					.and().build();
	}
	
}
