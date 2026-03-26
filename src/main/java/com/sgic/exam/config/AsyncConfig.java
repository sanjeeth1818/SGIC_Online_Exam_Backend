package com.sgic.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Core pool size: Keep 20 threads minimum for email sending
        executor.setCorePoolSize(20);
        // Max pool size: Scale up to 200 threads dynamically if there is a burst of 1000 students submitting
        executor.setMaxPoolSize(200);
        // Queue capacity: Queue up to 2000 emails instead of rejecting them (handling exactly 1000 students * 2 emails each)
        executor.setQueueCapacity(2000);
        // Name prefix
        executor.setThreadNamePrefix("EmailAsync-");
        executor.initialize();
        return executor;
    }
}
