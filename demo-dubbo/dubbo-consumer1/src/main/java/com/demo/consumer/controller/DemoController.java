package com.demo.consumer.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.rpc.RpcContext;
import com.demo.consumer.sonliasync.DealExportExcelThread;
import com.demo.consumer.thread.SpringContextHolder;
import com.demo.provider.interfaces.IDemoService;
import com.demo.provider.interfaces.UserVO;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 12:00
 */


@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private IDemoService iDemoService;

    @Autowired
    private ConsumerAsync consumerAsync;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @GetMapping("say")
//    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    @SentinelResource(value = "test", blockHandler = "exceptionHandler")
    public UserVO sayHi(String msg){

        System.out.println(Thread.currentThread().getName() + " controller-> " + RpcContext.getContext().get("USER"));
        System.out.println(Thread.currentThread().getName() + " controller-> " + RpcContext.getContext().getAttachment("USER"));
//        RpcContext.getContext().setAttachment("USER", "我是controller设置的值");

        return iDemoService.sayHi(msg);
    }


    @GetMapping("test")
    public String test(String msg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        System.out.println(Thread.currentThread().getName());


        consumerAsync.testAsync(msg);

        return msg;
    }


    @GetMapping("test2")
    public String test2(String msg)  {

            mockTest(msg);

        return msg;
    }

    private void mockTest(String msg){

        String user = String.valueOf(RpcContext.getContext().get("USER"));

        for(int i = 0;i<5;i++) {
            threadPoolTaskExecutor.submit(new DealExportExcelThread(user, msg +"_" + i));
        }


    }







    public String exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }

}
