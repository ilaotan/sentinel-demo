package com.demo.provider1.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 通过实现AsyncConfigurer自定义异常线程池，包含异常处理
 * 1 需要再Application.java里加上支持异步的注解  @EnableAsync // 开启异步任务支持
 * 2 在需要异步执行的方法上加上注解  @Async
 */
@Component
public class MyAsyncConfigurer implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(MyAsyncConfigurer.class);


//    #线程池配置
//    threadPool.corePoolSize=20
//    threadPool.maxPoolSize=50
//    threadPool.queueCapacity=1000
//    threadPool.keepAliveSeconds=300

    @Value("${spring.task.pool.corePoolSize:4}")
    private int corePoolSize;

    @Value("${spring.task.pool.maxPoolSize:1000}")
    private int maxPoolSize;

    @Value("${spring.task.pool.queueCapacity:500}")
    private int queueCapacity;

    @Value("${spring.task.pool.KeepAliveSeconds:21600}")
    private int keepAliveSeconds;

    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.corePoolSize);
        executor.setMaxPoolSize(this.maxPoolSize);
        executor.setQueueCapacity(this.queueCapacity);
        executor.setKeepAliveSeconds(this.keepAliveSeconds);
        executor.setThreadNamePrefix("MyExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }


    // 之前bean方式的 这个也行. 不需要implements AsyncConfigurer
    /*@Bean
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("MyExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }*/


    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }


    /**
     * 自定义异常处理类
     *
     * @author tanliansheng
     */
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            log.info("线程处理捕获异常 - " + throwable.getMessage());
            log.info("方法名 - " + method.getName());
            for (Object param : obj) {
                log.info("参数 - " + param);
            }
        }

    }

}