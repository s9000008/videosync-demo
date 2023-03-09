package com.videosync.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class VideoAspects {
/*
    @Before("execution(public * com.videosync.controller.VideoController.*(..))")
    public void checkSomethingBefore(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@annotation(com.bitsmuggler.learning.springbootaspect.aspects.CheckSomething)")
    public void checkSomethingAfter(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "@annotation(com.bitsmuggler.learning.springbootaspect.aspects.CheckSomething)", throwing = "ex")
    public void checkSomethingAfterThrowingAnException(JoinPoint joinPoint, Exception ex) {
        System.out.println(joinPoint.getSignature().getName());
    }

    @Around("@annotation(com.bitsmuggler.learning.springbootaspect.aspects.CheckSomething)")
    public Object checkSomethingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint.getSignature().getName());
        return null;
    }
*/

}
