package com.demo.consumer.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.RpcContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect   //定义一个切面
@Component
public class LogRecordAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(* com.demo.consumer.controller.DemoController.*(..))")
    public void excudeService() {
    }

    /**
     * 切面执行
     *
     * @param pjp AOP处理节点
     * @return
     * @throws Throwable
     */
    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        RpcContext.getContext().set("USER", "laotan from aop");

        Object result = pjp.proceed();
        return result;
    }
}
