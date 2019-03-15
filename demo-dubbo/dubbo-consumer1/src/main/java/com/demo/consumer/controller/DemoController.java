package com.demo.consumer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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


    @GetMapping("say")
//    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    @SentinelResource(value = "test", blockHandler = "exceptionHandler")
    public UserVO sayHi(String msg) {


        return iDemoService.sayHi(msg);
    }


    public String exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }

}
