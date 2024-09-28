package com.azry.dmtp.z.aspect;

import com.azry.dmtp.z.model.entity.LogEntry;
import com.azry.dmtp.z.service.LogEntryService;
import com.azry.dmtp.z.service.implementation.LoggedInUserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class LoggingAspect {

    private final LogEntryService logEntryService;
    private final LoggedInUserServiceImpl loggedInUserService;

    @Around("@annotation(com.azry.dmtp.z.annotation.LogControllerUsage)")
    public Object logControllerUsage(ProceedingJoinPoint joinPoint) throws Throwable {

        String username = loggedInUserService.getLoggedInUsername();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        LogEntry logEntry = LogEntry.builder()
                .username(username)
                .methodName(methodName)
                .methodArguments(args)
                .callTime(LocalDateTime.now())
                .build();
        LogEntry savedLogEntry = logEntryService.addLogEntry(logEntry);
        log.info("Method {} with parameters {} was called by user {}", savedLogEntry.getMethodName(), savedLogEntry.getMethodArguments(), savedLogEntry.getUsername());

        return joinPoint.proceed();
    }
}
