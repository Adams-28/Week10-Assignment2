package net.skillspire.library_system;


import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    public LoggingAspect() {
        System.out.println("Test logging");
    }

    // Creating reusable pointcut method
    @Pointcut("execution(* net.skillspire.library_system.LibraryService.*(..))")
    public void serviceMethods() {

    }


    // Implementing @Before advice
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        Object[] arguments = joinPoint.getArgs();

        System.out.println("\n--- Logging Aspect: @Before ---");
        System.out.println("(Logging) Calling method: " + methodName);
        System.out.println("(Logging) Arguments: " + Arrays.toString(arguments));
    }

    // Implementing @AfterReturning advice
    @AfterReturning(
            pointcut = "serviceMethods()",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {

        String methodName = joinPoint.getSignature().getName();

        System.out.println("(Logging) Method completed: " + methodName);

        System.out.println("(Logging) Returned value: " + result);
    }


    // Implementing @AfterThrowing Advice
    @AfterThrowing(
            pointcut = "serviceMethods()",
            throwing = "exception"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {

        String methodName = joinPoint.getSignature().getName();

        System.out.println("(Logging) Exception in method: " + methodName);

        System.out.println("(Logging) Exception type: " +
                exception.getClass().getSimpleName());

        System.out.println("(Logging) exception message: " + exception.getMessage());
    }
}
