package com.demo.provider1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.provider.interfaces.UserVO;
import com.demo.provider1.async.AsyncService;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 21:06
 */
@Component
public class MyCommandLine implements CommandLineRunner {

    @Autowired
    private AsyncService asyncService;

    @Override
    public void run(String... args) throws Exception {

        List<UserVO> list = new ArrayList<>();

        for(int i = 0;i< 20;i++) {
            System.out.println("执行第" + i);
            Future<UserVO> future = asyncService.getUserVo(i + "");
            UserVO userVO = null;
            try {
                userVO = future.get();
                list.add(userVO);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("线程执行结束");
        for (UserVO userVO: list) {
            System.out.println(userVO.getName());
        }

    }
}
