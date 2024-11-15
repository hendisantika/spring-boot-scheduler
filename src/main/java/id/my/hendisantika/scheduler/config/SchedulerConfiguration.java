package id.my.hendisantika.scheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-scheduler
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/11/24
 * Time: 11.07
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "spring.scheduler.enabled")
public class SchedulerConfiguration implements SchedulingConfigurer {

    @Value("${spring.scheduler.delayinsec}")
    private Long delay;

    @Value("${spring.task.scheduling.pool.size}")
    private int poolSize;

    @Value("${spring.scheduler.cron}")
    private String cronExpression;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
//        taskRegistrar.addTriggerTask(
//                () -> System.out.println("Scheduling task at "+ System.currentTimeMillis()),
//                triggerContext -> {
//                    Optional<Instant> lastCompletionTime = Optional.ofNullable(triggerContext.lastCompletion());
//                    Instant nextExecutionTime = lastCompletionTime.orElseGet(Instant::now).plusMillis(delay * 1000);
//                    return nextExecutionTime;
//                });
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(poolSize);
        scheduler.setThreadNamePrefix("ThreadScheduler-");
        scheduler.initialize();
        return scheduler;
    }

//    @Bean
//    public CronTrigger cronTrigger(){
//        return new CronTrigger(cronExpression);
//    }
}
