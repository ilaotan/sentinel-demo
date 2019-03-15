package com.demo.consumer.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.RpcContext;
import com.demo.consumer.thread.SpringContextHolder;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/15 10:43
 */

@Component
public class ConsumerAsync {


    @Async
    public void testAsync(String msg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        System.out.println(Thread.currentThread().getName());

        RpcContext.getContext().setAttachment("USER", msg);
        Method method = DemoController.class.getMethod("sayHi", String.class);
        method.invoke(SpringContextHolder.getBean(DemoController.class), "我是反射执行的");

    }


}
