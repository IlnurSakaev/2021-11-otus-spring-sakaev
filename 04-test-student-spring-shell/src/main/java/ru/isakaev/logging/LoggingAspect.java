package ru.isakaev.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(ru.isakaev.logging.LogAnnotation)")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        LOG.debug("Start method in ru.isakaev.dao.QuestionDaoImpl.getInputStream");

        Object o = null;
        try {
           o = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LOG.debug("Result method is " + o + System.lineSeparator());

        return o;
    }
}
