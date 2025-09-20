package vn.ecornomere.ecornomereAZ.config;

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
            executor.setCorePoolSize(5); // số luồng tối thiểu
            executor.setMaxPoolSize(10); // số luồng tối đa
            executor.setQueueCapacity(100); // hàng đợi
            executor.setThreadNamePrefix("AsyncThread-");
            executor.initialize();
            return executor;
      }

}
