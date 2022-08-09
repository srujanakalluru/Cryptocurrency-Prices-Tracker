package com.logging;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import static com.logging.LoggingBean.ApiType.*;

@Aspect
@Component
@Getter
@Setter
@Slf4j(topic = "CryptoPricesTrackerApplication")
public class LoggingAspect {

    @Pointcut("execution(* com.controller..*(..))")
    public void controllerPointCut() {
        // Method is empty as this is just a Pointcut
    }

    @Pointcut("execution(* com.repository..*(..))")
    public void repositoryPointCut() {
        // Method is empty as this is just a Pointcut
    }

    @Pointcut("execution(* com.service..*(..))")
    public void servicePointCut() {
        // Method is empty as this is just a Pointcut
    }

    @Pointcut("execution(* com.scheduler..*(..))")
    public void schedulerPointCut() {
        // Method is empty as this is just a Pointcut
    }

    @Pointcut("execution(* com.client..*(..))")
    public void externalServicePointCut() {
        // Method is empty as this is just a Pointcut
    }

    @Pointcut("execution(* com..*(..))")
    private void allClassesPointCut() {
        // Method is empty as this is just a Pointcut
    }

    @Around("schedulerPointCut()")
    public Object logAroundScheduler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logBean(proceedingJoinPoint, SCHEDULER);

    }

    @Around("controllerPointCut()")
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logBean(proceedingJoinPoint, CONTROLLER);
    }

    @Around("servicePointCut()")
    public Object logAroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logBean(proceedingJoinPoint, SERVICE);
    }

    @Around("externalServicePointCut()")
    public Object logAroundExternalService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logBean(proceedingJoinPoint, EXTERNAL);
    }

    @Around("repositoryPointCut()")
    public Object logAroundRepository(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logBean(proceedingJoinPoint, REPOSITORY);
    }


    @AfterThrowing(value = "controllerPointCut()", throwing = "ex")
    public void logAfterThrowingExceptionCall(JoinPoint joinPoint, Throwable ex) {
        logErrorBean(joinPoint, ex);
    }

    private void logErrorBean(JoinPoint joinPoint, Throwable ex) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        LoggingBean bean = LoggingBean.builder()
                .className(joinPoint.getTarget().getClass().getSimpleName())
                .method(signature.getName())
                .parameters(signature.getParameterNames())
                .arguments(joinPoint.getArgs())
                .stackTrace(ex.getMessage())
                .build();
        log.error(bean.toString());

    }


    private Object logBean(ProceedingJoinPoint proceedingJoinPoint, LoggingBean.ApiType apiType) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();

        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();

        LoggingBean bean = LoggingBean.builder()
                .apiType(apiType)
                .className(proceedingJoinPoint.getTarget().getClass().getSimpleName())
                .method(signature.getName())
                .parameters(signature.getParameterNames())
                .arguments(proceedingJoinPoint.getArgs())
                .returnValue(object)
                .durationMs(endTime - startTime)
                .build();
        if (log.isInfoEnabled())
            log.info(bean.toString());
        return object;
    }

}
