package com.logging;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    @Pointcut("execution(* com.scheduler.impl.CryptoPricesTrackingSchedulerServiceImpl.scheduleStart(..))")
    public void schedulerStartPointCut() {
        // Method is empty as this is just a Pointcut
    }

    @Pointcut("execution(* com.scheduler.impl.CryptoPricesTrackingSchedulerServiceImpl.scheduleStop(..))")
    public void schedulerStopPointCut() {
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


    @Before("schedulerStartPointCut()")
    public void logBeforeSchedulerStart(JoinPoint joinPoint) throws Throwable {
        logBeforeBean(joinPoint, SCHEDULER, "Starting the scheduler");
    }

    @Before("schedulerStopPointCut()")
    public void logBeforeSchedulerStop(JoinPoint joinPoint) throws Throwable {
        logBeforeBean(joinPoint, SCHEDULER, "Stopping the scheduler");
    }

    @Around("controllerPointCut()")
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logAroundBean(proceedingJoinPoint, CONTROLLER);
    }

    @Around("servicePointCut()")
    public Object logAroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logAroundBean(proceedingJoinPoint, SERVICE);
    }

    @Around("externalServicePointCut()")
    public Object logAroundExternalService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logAroundBean(proceedingJoinPoint, EXTERNAL);
    }

    @Around("repositoryPointCut()")
    public Object logAroundRepository(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logAroundBean(proceedingJoinPoint, REPOSITORY);
    }


    @AfterThrowing(value = "controllerPointCut()", throwing = "ex")
    public void logAfterThrowingExceptionCall(JoinPoint joinPoint, Throwable ex) {
        logErrorBean(joinPoint, ex);
    }

    private void logErrorBean(JoinPoint joinPoint, Throwable ex) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        LoggingBean bean = LoggingBean.builder()
                .apiType(ERROR)
                .className(joinPoint.getTarget().getClass().getSimpleName())
                .method(signature.getName())
                .parameters(signature.getParameterNames())
                .arguments(joinPoint.getArgs())
                .stackTrace(null != ex.getMessage() ? ex.getMessage() : ex.getCause().getMessage())
                .build();
        log.error(bean.toString());

    }


    private void logBeforeBean(JoinPoint joinPoint, LoggingBean.ApiType apiType, String detailMessage) throws Throwable {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        LoggingBean bean = LoggingBean.builder()
                .apiType(apiType)
                .className(joinPoint.getTarget().getClass().getSimpleName())
                .method(signature.getName())
                .parameters(signature.getParameterNames())
                .arguments(joinPoint.getArgs())
                .detailMessage(detailMessage)
                .build();
        if (log.isInfoEnabled())
            log.info(bean.toString());
    }


    private Object logAroundBean(ProceedingJoinPoint proceedingJoinPoint, LoggingBean.ApiType apiType) throws Throwable {
        return logAroundBean(proceedingJoinPoint, apiType, null);
    }

    private Object logAroundBean(ProceedingJoinPoint proceedingJoinPoint, LoggingBean.ApiType apiType, String detailMessage) throws Throwable {
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
                .detailMessage(detailMessage)
                .build();
        if (log.isInfoEnabled())
            log.info(bean.toString());
        return object;
    }

}
