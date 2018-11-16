package com.iexample.itoutaio.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.iexample.itoutaio.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint)
    {
        StringBuilder sb =new StringBuilder();
        for(Object arg :joinPoint.getArgs())
        {
            sb.append("args:"+arg.toString()+"|");
        }
        logger.info("before LogAspect"+sb.toString());
    }
    @After("execution(* com.iexample.itoutaio.controller.indexController.*(..))")
    public void afterMethod()
    {
        logger.info("After LogAspect");
    }
}
