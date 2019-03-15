package com.demo.provider1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 11:43
 */

@Configuration
@ImportResource("classpath:dubbo/spring-dubbo.xml")
public class DubboConfiguration {


}
