package org.sticker.pack.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
@Aspect
@Component
public class RepeaterAspect {

    @Around("@annotation(org.sticker.pack.aspect.Repeater)")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        System.out.println(pjp.getClass().getName());
        Boolean proceed = (Boolean)pjp.proceed();
        // stop stopwatch
        return Boolean.FALSE;
    }
}
