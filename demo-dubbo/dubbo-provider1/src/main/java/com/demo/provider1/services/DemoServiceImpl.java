package com.demo.provider1.services;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.demo.provider.interfaces.IDemoService;
import com.demo.provider.interfaces.UserVO;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 11:50
 */

@Service("demoService")
public class DemoServiceImpl implements IDemoService {


    @Override
    public UserVO sayHi(String name) {

        String user = RpcContext.getContext().getAttachment("USER");

        System.out.println("dubbo提供者拿传参  -> " + name);
        System.out.println("dubbo提供者拿隐式传参  -> " + user);


        UserVO userVO = new UserVO();
        userVO.setName("hello " + name);
        return userVO;
    }
}
