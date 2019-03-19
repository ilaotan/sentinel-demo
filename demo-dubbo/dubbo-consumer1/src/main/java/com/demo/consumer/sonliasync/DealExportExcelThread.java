package com.demo.consumer.sonliasync;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.alibaba.dubbo.rpc.RpcContext;
import com.demo.consumer.controller.DemoController;
import com.demo.consumer.thread.SpringContextHolder;
import com.demo.dubbo.filter.CommonConstants;

/**
 * 处理EXCEL线程导出方案
 */
public class DealExportExcelThread implements Callable<String> {

    private String userName;

    private String msg;


    public DealExportExcelThread(String userName, String msg) {
        this.userName = userName;
        this.msg = msg;
    }

    @Override
    public String call() throws Exception {

//        RpcContext.getContext().setAttachment(CommonConstants.USERID, userName + "~~~在线程类里加点料(Attachment)");
        RpcContext.getContext().set(CommonConstants.USERID, userName + "~~~在线程类里加点料(NO)");
        Method method = DemoController.class.getMethod("sayHi", String.class);
        Object object = method.invoke(SpringContextHolder.getBean(DemoController.class), msg + "我是反射传参");

        System.out.println(Thread.currentThread().getName() + "线程执行:" + object);
        return Thread.currentThread().getName() + "线程执行:" + object;
    }
}