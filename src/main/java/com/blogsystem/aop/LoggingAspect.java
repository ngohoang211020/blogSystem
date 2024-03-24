package com.blogsystem.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Aspect for logging execution of api
 */
@Aspect
@Component
@Log4j2
public class LoggingAspect {
    @Around("execution(* com.blogsystem.controller.*.*(..))")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        var signature = (MethodSignature) pjp.getSignature();
        var method = signature.getMethod();
        var info = method.getName() + " - (" + pjp.getSignature().getDeclaringTypeName() + ")";
        var start = System.currentTimeMillis();
        log.info("Going to call the method:" + info);
        var output = pjp.proceed(pjp.getArgs());
        var elapsedTime = System.currentTimeMillis() - start;
        log.info("Method execution time: " + elapsedTime + " miliseconds.");
        return output;
    }
}
