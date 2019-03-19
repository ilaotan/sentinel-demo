package com.demo.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 11:43
 */

@Configuration
@ImportResource("classpath:dubbo/spring-dubbo.xml")
public class DubboConfiguration {


//    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

}
