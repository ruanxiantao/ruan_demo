package com.beijing.ruan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: ruanxiantao
 * @date: 2020/8/29 11:27
 */
@Configuration
public class ThreadPoolUtil {
    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                                        2,
                                        16,
                                        10,
                                        TimeUnit.SECONDS,
                                        new ArrayBlockingQueue<>(5),
                                        Executors.defaultThreadFactory(),
                                        new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
