package com.demo.consumer;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2019/3/14 11:14
 */

@SpringBootApplication(scanBasePackages = {"com.demo"})
@EnableAsync
public class Consumer1Application {

    private static final String RES_KEY = "com.demo.provider.interfaces.IDemoService:sayHi(java.lang.String)";


    public static void main(String[] args) {

        initFlowRule();


        SpringApplication app = new SpringApplication(Consumer1Application.class);
        app.run(args);

    }

    private static void initFlowRule() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(RES_KEY);
        flowRule.setCount(11);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");

        FlowRule flowRule2 = new FlowRule();
        flowRule2.setResource("test");
        flowRule2.setCount(1);
        flowRule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule2.setLimitApp("default");


        FlowRuleManager.loadRules(Arrays.asList(flowRule, flowRule2));
    }



}
