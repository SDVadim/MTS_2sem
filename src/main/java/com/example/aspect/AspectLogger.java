package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.time.Duration;
import java.time.Instant;

@Component
@Aspect
public class AspectLogger {
  @Before("execution( * com.example.controller.*.*( .. ))")
  public void logBefore(JoinPoint joinPoint) {
    System.out.println("Вызвано перед методом: " + joinPoint.getSignature().getName());
  }

  @Around("execution( * com.example.controller.*.*( .. ))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Instant startTime = Instant.now();
    Object result = joinPoint.proceed();
    Instant endTime = Instant.now();
    System.out.println("Время выполнения метода " + joinPoint.getSignature().getName() + " равно " +
        Duration.between(startTime, endTime).toMillis());
    return result;
  }
}
