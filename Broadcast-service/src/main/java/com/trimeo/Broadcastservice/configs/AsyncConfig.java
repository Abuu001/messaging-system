package com.trimeo.Broadcastservice.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Data
@EnableAsync
@Configuration
@AllArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    @NonNull
    private AsyncValues asyncValues;

    @Bean("threadPoolTaskExecutor")
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncValues.getThreadPoolSize());
        executor.setMaxPoolSize(asyncValues.getMaxThreadPoolSize());
        executor.initialize();
        return executor;
    }
}
