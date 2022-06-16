package cc.stacks.mercury.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean("ZtTask")
    public Executor noticeExecutor() {
        ThreadPoolTaskExecutor executor = buildExecutor(1,2,4,8);
        executor.setThreadNamePrefix("zt-task-");
        executor.initialize();
        return executor;
    }

    private ThreadPoolTaskExecutor buildExecutor(int min,int max,int queue,int keep){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(min);
        // 最大线程数
        executor.setMaxPoolSize(max);
        // 等待队列大小
        executor.setQueueCapacity(queue);
        // 最大空闲时间
        executor.setKeepAliveSeconds(keep);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return executor;
    }

}