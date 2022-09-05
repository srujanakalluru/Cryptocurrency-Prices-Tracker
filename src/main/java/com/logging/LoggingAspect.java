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

    /**
     * Pointcut for controller
     */
    @Pointcut("execution(* com.controller..*(..))")
    public void controllerPointCut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Pointcut for repository
     */
    @Pointcut("execution(* com.repository..*(..))")
    public void repositoryPointCut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Pointcut for service
     */
    @Pointcut("execution(* com.service..*(..))")
    public void servicePointCut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Pointcut for scheduler start method
     */
    @Pointcut("execution(* com.scheduler.impl.CryptoPricesTrackingSchedulerServiceImpl.scheduleStart(..))")
    public void schedulerStartPointCut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Pointcut for scheduler stop method
     */
    @Pointcut("execution(* com.scheduler.impl.CryptoPricesTrackingSchedulerServiceImpl.scheduleStop(..))")
    public void schedulerStopPointCut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * Pointcut for external client calls
     */
    @Pointcut("execution(* com.client..*(..))")
    public void externalServicePointCut() {
        // Method is empty as this is just a Pointcut
    }

    /**
     * @param joinPoint joinPoint
     */
    @Before("schedulerStartPointCut()")
    public void logBeforeSchedulerStart(JoinPoint joinPoint) {
        logBeforeBean(joinPoint, SCHEDULER, "Starting the scheduler");
    }

    /**
     * @param joinPoint joinPoint
     */
    @Before("schedulerStopPointCut()")
    public void logBeforeSchedulerStop(JoinPoint joinPoint) {
        logBeforeBean(joinPoint, SCHEDULER, "Stopping the scheduler");
    }

    /**
     * @param proceedingJoinPoint proceedingJoinPoint
     * @throws Throwable throwable
     */
    @Around("controllerPointCut()")
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logAroundBean(proceedingJoinPoint, CONTROLLER);
    }

    /**
     * @param proceedingJoinPoint proceedingJoinPoint
     * @throws Throwable throwable
     */
    @Around("servicePointCut()")
    public Object logAroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logAroundBean(proceedingJoinPoint, SERVICE);
    }

    /**
     * @param proceedingJoinPoint proceedingJoinPoint
     * @throws Throwable throwable
     */
    @Around("externalServicePointCut()")
    public Object logAroundExternalService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logAroundBean(proceedingJoinPoint, EXTERNAL);
    }

    /**
     * @param proceedingJoinPoint proceedingJoinPoint
     * @throws Throwable throwable
     */
    @Around("repositoryPointCut()")
    public Object logAroundRepository(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logAroundBean(proceedingJoinPoint, REPOSITORY);
    }

    /**
     * @param joinPoint joinPoint
     */
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


    /**
     * @param joinPoint     joinPoint
     * @param apiType       apiType
     * @param detailMessage detailMessage
     */
    private void logBeforeBean(JoinPoint joinPoint, LoggingBean.ApiType apiType, String detailMessage) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        LoggingBean bean = LoggingBean.builder()
                .apiType(apiType)
                .className(joinPoint.getTarget().getClass().getSimpleName())
                .method(signature.getName())
                .parameters(signature.getParameterNames())
                .arguments(joinPoint.getArgs())
                .detailMessage(detailMessage)
                .build();
        log.info(bean.toString());
    }


    /**
     * @param proceedingJoinPoint proceedingJoinPoint
     * @param apiType             apiType
     * @return Object
     * @throws Throwable throwable
     */
    private Object logAroundBean(ProceedingJoinPoint proceedingJoinPoint, LoggingBean.ApiType apiType) throws Throwable {
        return logAroundBean(proceedingJoinPoint, apiType, null);
    }

    /**
     * @param proceedingJoinPoint proceedingJoinPoint
     * @param apiType             apiType
     * @param detailMessage       detailMessage
     * @return Object
     * @throws Throwable throwable
     */
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
        log.info(bean.toString());
        return object;
    }

}
