package com.springboot.boot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP helps us deal with the cross-cutting concerns in Java.
 * These are the concerns which goes through multiple classes.
 * Example: Transactional Aspect or Logging Aspect.
 * 
 * Central points of AOP are:
 * 
 * 1. Aspect: This is just a class like this one which addresses 
 * one of the cross-cutting concerns.
 * 
 * 2. Advice: A process (basically a piece of code) that should
 * run around, before or after a Join-point.
 * 
 * 3. Join-Point: Join-point refers to an ongoing process around
 * which an advice can run. In Spring AOP a join-point usually 
 * refers to a method Invocation.
 * 
 * 4. Pointcut: It defines the area or range of interest to identify
 * the Join-point. In simple words, this defines an area out of the 
 * whole project where you need an advice to run.
 * 
 * Pointcut can be defined as a method signature and this method 
 * can then be used with an Advice method.
 * 
 * Alternatively, a point cut can be defined in place with the 
 * Advice annotation.
 * 
 * For more details on spring AOP, visit:
 * https://docs.spring.io/spring/docs/2.5.x/reference/aop.html
 * https://www.baeldung.com/spring-aop-pointcut-tutorial
 * @author saurabhtiwari
 *
 */

@Aspect
@Component
public class LoggingAspect {
	/**
	 * @Around("execution(*  com.springboot.service.*.*(..)) ")
	 * represents the Around advice,
	 * first wild-card * represents method with any return type.
	 * second * represents any class in service package.
	 * third * represents any method in class.
	 * (..) represents method with any argument.
	 * We can customize all three things above like:
	 * @Around("execution(String  com.springboot.service.methodA(Long)) ")
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	  @Around("execution(*  com.springboot.services.*.*(..)) ")
	  public Object logAround(ProceedingJoinPoint joinPoint ) throws Throwable {
		System.out.println("Entering :: " + joinPoint.getSignature().getName());	
	    Object result = joinPoint.proceed();
		System.out.println("Exiting :: " + joinPoint.getSignature().getName());
	    return result;
	  }
}
