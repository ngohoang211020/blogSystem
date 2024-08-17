package com.blogsystem.cronjob.base;

import com.blogsystem.cronjob.TestTask1;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Log4j2
public class SchedulingConfig implements SchedulingConfigurer {
    private final ApplicationContext context;
    private final Environment environment;
    private final ServiceConfig serviceConfig;
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        var poolSize = environment.getRequiredProperty("task-scheduler.pool-size", Integer.class);
            var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("blog-system-task-scheduler-");
        log.info("Initialized custom task scheduler with properties {poolSize: {}}", poolSize);
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
      taskRegistrar.addCronTask(context.getBean(TestTask1.class), serviceConfig.getCronExpression(ScheduledTask.TASK_TEST_TASK_1));
      taskRegistrar.addCronTask(context.getBean(TestTask1.class), serviceConfig.getCronExpression(ScheduledTask.TASK_TEST_TASK_2));
      taskRegistrar.setScheduler(threadPoolTaskScheduler());
        log.info("Registered {} scheduled task", taskRegistrar.getCronTaskList().size());
    }
}
