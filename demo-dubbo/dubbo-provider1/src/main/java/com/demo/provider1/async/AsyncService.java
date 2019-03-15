package com.demo.provider1.async;

import java.util.Random;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.demo.provider.interfaces.UserVO;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 20:50
 */

@Component
public class AsyncService {



    @Async
    public Future<UserVO> getUserVo(String name) {
        Random random =new Random();
        //模拟耗时操作。
        try {
            long sleepTime = random.nextInt(10) * 1000;
            System.out.println(name + " 模拟操作耗时开始 " + sleepTime);
            Thread.sleep(sleepTime);
        }catch (Exception e) {

        }
        UserVO userVO = new UserVO();
        userVO.setName("your num is " + name);
        return new AsyncResult<>(userVO);
    }

}
