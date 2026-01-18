package com.example.demo.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

  @Before("execution(* com.example.demo.controller.*.*(..))")
  public void logBeforeControllerMethods(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    String methodName = signature.getName();
    String className = joinPoint.getTarget().getClass().getName();
    Object[] args = joinPoint.getArgs();
    log.info("Entering Controller: {}.{} with arguments: {}", className, methodName, args);
  }

  @After("execution(* com.example.demo.controller.*.*(..))")
  public void logAfterControllerMethods(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    String methodName = signature.getName();
    String className = joinPoint.getTarget().getClass().getName();
    Object[] args = joinPoint.getArgs();
    log.info("Exiting Controller: {}.{} with arguments: {}", className, methodName, args);
  }

  @Before("execution(* com.example.demo.service.*.*(..))")
  public void logBeforeServiceMethods(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    String methodName = signature.getName();
    String className = joinPoint.getTarget().getClass().getName();
    Object[] args = joinPoint.getArgs();
    log.info("Entering Service: {}.{} with arguments: {}", className, methodName, args);
  }

  @After("execution(* com.example.demo.service.*.*(..))")
  public void logAfterServiceMethods(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    String methodName = signature.getName();
    String className = joinPoint.getTarget().getClass().getName();
    Object[] args = joinPoint.getArgs();
    log.info("Exiting Service: {}.{} with arguments: {}", className, methodName, args);
  }
}
