package com.spring.testing.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.spring.testing.model.LoginResponse;

@Aspect
@Component
public class AuthAspect {

	@Around(value = "execution(* com.spring.testing.service.AuthService.attemptLogin(..))")
	public LoginResponse aroundAdviceForAttemptLoginInService(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("attemptLogin function started in AuthService");
		try {
			return (LoginResponse) joinPoint.proceed();
		} catch (Exception e) {
			System.out.println("attemptLogin function failed in AuthService");
		}
		System.out.println("attemptLogin function ended in AuthService");
		return null;
	}
	
}
