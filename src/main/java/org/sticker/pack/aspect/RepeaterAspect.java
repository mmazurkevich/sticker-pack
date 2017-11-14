package org.sticker.pack.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
@Aspect
public class RepeaterAspect {

    @Around("@annotation(org.sticker.pack.aspect.Repeater)")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        System.out.println(pjp.getClass().getName());
        pjp.proceed();
        pjp.proceed();
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }
}
