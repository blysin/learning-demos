package com.blysin.demo.netty.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author daishaokun
 * @date 2019-08-28
 */
@Configuration
public class ExecutorConfig {

    @Bean("threadPoolExecutor")
    public ThreadPoolExecutor taskExecutor() {
        return new ThreadPoolExecutor(1, getBestPoolSize(), 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), r -> new Thread(r, "taskExecutor-"));
    }

    /**
     * 根据 Java 虚拟机可用处理器数目返回最佳的线程数。<br>
     * 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)，
     */
    private static int getBestPoolSize() {
        try {
            // JVM可用处理器的个数
            final int cores = Runtime.getRuntime().availableProcessors();
            // 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)
            return (int) (cores / (1 - 0.7));
        } catch (Throwable e) {
            // 异常发生时姑且返回10个任务线程池
            return 10;
        }
    }
}