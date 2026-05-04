package org.lx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Title: AsyncConfig
 * @Author: MrLu2
 * @Package: org.lx.config
 * @Date: 2026/5/4 14:47
 * @Description: 配置线程池
 */

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("asyncTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);        // 核心线程数
        executor.setMaxPoolSize(8);         // 最大线程数
        executor.setQueueCapacity(100);     // 队列容量
        executor.setThreadNamePrefix("async-task-");
        executor.initialize();
        return executor;
    }
}
