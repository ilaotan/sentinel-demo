package com.demo.provider1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 11:37
 */

@SpringBootApplication(scanBasePackages = {"com.demo"})
public class ProviderApplication {


    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(ProviderApplication.class);
        app.run(args);

    }
}
