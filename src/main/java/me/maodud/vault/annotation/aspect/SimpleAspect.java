package me.maodud.vault.annotation.aspect;

import me.maodud.vault.annotation.MethodLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SimpleAspect {

    @Around(value = "@annotation(me.maodud.vault.annotation.MethodLog) && @annotation(methodLog)", argNames = "pjp, methodLog")
    public Object methodLogAnnoProcessor(ProceedingJoinPoint pjp, MethodLog methodLog) throws Throwable {
        String thread = Thread.currentThread().getName();
        String methodName = pjp.getSignature().getName();
        long start = System.currentTimeMillis();
        Object data = pjp.proceed();
        long end = System.currentTimeMillis();
        long exeTime = end - start;

        if (exeTime > methodLog.time()) {
            exeTime = methodLog.defaultValue();
        }

        System.out.println("Thread: " + thread + ", Method: " + methodName
                + " executed in " + exeTime + " ms");

        return data;
    }
}
