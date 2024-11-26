package com.wick.boot.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池工具类
 * 2核4G服务器推荐配置
 * 
 * @author Wickson
 */
@Slf4j
@Configuration
public class ThreadPoolUtils {

    /**
     * 核心线程数 = CPU核心数 + 1
     */
    @Value("${thread.pool.core-size:3}")
    private int corePoolSize;

    /**
     * 最大线程数 = CPU核心数 * 2
     */
    @Value("${thread.pool.max-size:4}")
    private int maxPoolSize;

    /**
     * 任务队列容量
     */
    @Value("${thread.pool.queue-capacity:200}")
    private int queueCapacity;

    /**
     * 线程空闲时间
     */
    @Value("${thread.pool.keep-alive-seconds:60}")
    private int keepAliveSeconds;

    @Bean("commonExecutor")
    public Executor commonExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 任务队列容量
        executor.setQueueCapacity(queueCapacity);
        // 线程前缀名
        executor.setThreadNamePrefix("common-executor-");
        // 线程空闲时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 拒绝策略：调用线程执行任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务完成后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    @Bean("asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("async-executor-");
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}