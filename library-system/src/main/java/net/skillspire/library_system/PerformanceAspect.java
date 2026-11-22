package net.skillspire.library_system;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    // Creating reusable pointcut
    @Pointcut("execution(* net.skillspire.library_system.*Repository.*(..))")
    public void repositoryMethods() {

    }

    // Creating method to test execution time on all methods
    @Around("repositoryMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint)
        throws Throwable {

        long startTime = System.nanoTime();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.nanoTime();

            long duration = endTime - startTime;

            System.out.println("(Performance) method: " + joinPoint.getSignature().getName());

            System.out.println("(Performance) Execution time: " + duration / 1_000_000.0 + " ms");
        }
    }

}
