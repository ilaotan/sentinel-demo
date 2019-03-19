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



}
