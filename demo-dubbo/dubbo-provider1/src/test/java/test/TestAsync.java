package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.provider.interfaces.UserVO;
import com.demo.provider1.ProviderApplication;
import com.demo.provider1.async.AsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 20:59
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
public class TestAsync {

    @Autowired
    private AsyncService asyncService;


    @Test
    public void test() {

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

        for (UserVO userVO: list) {
            System.out.println(userVO.getName());
        }

    }

}
