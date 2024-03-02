package ru.sberbank.edu.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around("@annotation(ToLog)")
    public Object log(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Start method " + methodName + " with parameters " + Arrays.asList(args));

        Object returnedByMethod;
        try {
            returnedByMethod = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        log.info("Method" + methodName + " has been executed and return " + returnedByMethod);

        return returnedByMethod;
    }
}
