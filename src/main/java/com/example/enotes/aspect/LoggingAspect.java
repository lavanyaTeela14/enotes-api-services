package com.example.enotes.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /*@Before("execution(* com.example.enotes.controller.*.*(..))")
    public void beforeController(JoinPoint joinPoint) {
        Signature signature= joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Calling :: {} :: {}()",className,methodName);
    }*/

    /*@After("execution(* com.example.enotes.controller.*.*(..))")
    public void AfterController(JoinPoint joinPoint)
    {
        Signature signature=joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("End calling :: {} :: {}()",className,methodName);
    }*/

    @Around("execution(* com.example.enotes.controller.*.*(..))")
    public Object JoinPointController(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Signature signature=joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Calling :: {} :: {}() ",className,methodName);
        long start=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        long duration=System.currentTimeMillis()-start;
        log.info("Execution ending :: {} :: {}() :: {}ms",className,methodName,duration);
        return result;
    }

    @Around("execution(* com.example.enotes.service.*.*(..))")
    public Object JoinPointService(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Signature signature=joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Calling :: {} :: {}() ",className,methodName);
        long start=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        long duration=System.currentTimeMillis()-start;
        log.info("Execution ending :: {} :: {}() :: {}ms",className,methodName,duration);
        return result;
    }
}
