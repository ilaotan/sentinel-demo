package com.demo.consumer.config;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.rpc.RpcContext;
import com.demo.dubbo.filter.CommonConstants;
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
    public Object doAround(ProceedingJoinPoint pjp) {
        String uri = "";

        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            uri = request.getRequestURI();
        }catch (Exception e) {
//            e.printStackTrace();
        }



        Object result = null;
        try {

            // RpcContext.getContext() 是与线程绑定的. 而线程在spring里是可复用的.导致之前设置过.并且没清除过.这里会再次拿到.
            Object o = RpcContext.getContext().get(CommonConstants.USERID);
            System.out.println(Thread.currentThread().getName() + " 当前切面获得的userid为 " + o + "  " + uri);
            if(o == null) {
                RpcContext.getContext().set(CommonConstants.USERID, "laotan from aop");
            }


            result = pjp.proceed();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            // test 尝试移除
            RpcContext.getContext().remove(CommonConstants.USERID);
        }
        return result;
    }
}
