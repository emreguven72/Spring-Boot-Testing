package com.spring.testing.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoAspect {
	
	
	
	
	@Around(value = "execution(* com.spring.testing.controller.DemoController.publicMessage(..))")
	public String aroundAdviceForGetPublicMessageFromController(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("publicMessage function in DemoController has started at " + new Date()); 
		
		try {
			return (String) joinPoint.proceed();
		} catch (Exception e) {
			System.out.println("publicMessage function in DemoController failed!"); 
		}
		
		System.out.println("publicMessage function in DemoController has ended at " + new Date());
		return null;
	}
	
	@Around(value = "execution(* com.spring.testing.controller.DemoController.securedMessage(..))")
	public String aroundAdviceForGetSecuredMessageFromController(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("securedMessage function in DemoController has started at " + new Date()); 
		
		try {
			return (String) joinPoint.proceed();
		} catch (Exception e) {
			System.out.println("securedMessage function in DemoController failed!"); 
		}
		
		System.out.println("securedMessage function in DemoController has ended at " + new Date());
		return null;
	}
	
}
