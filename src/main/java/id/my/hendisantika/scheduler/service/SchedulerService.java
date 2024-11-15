package id.my.hendisantika.scheduler.service;

import id.my.hendisantika.scheduler.entity.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-scheduler
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/11/24
 * Time: 11.04
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SchedulerService {
    private final TaskScheduler taskScheduler;
    private final WebClient webClient;
    private final Map<String, Job> schedulerMap = new HashMap<>();

    public Map<String, Job> getScheduledJobs() {
        return schedulerMap;
    }

    public void deleteScheduledJob(String jobId) {
        if (schedulerMap.containsKey(jobId))
            schedulerMap.remove(jobId);
        else
            log.error("No jobs exists with Id:{}", jobId);
    }

    public void scheduleGetJob(Job job) {
        log.info("scheduling get job:{}", job.toString());
        taskScheduler.schedule(
                () -> {
                    ResponseEntity<String> response = webClient.get()
                            .uri(job.getBaseURL() + job.getApiURL())
                            .header("Content-Type", "application/json")
                            .retrieve()
                            .toEntity(String.class)
                            .block();
                    if (response.getStatusCode().isError()) {
                        log.error("Failure calling Job:{}with URL:{}{}", job.getJobName(), job.getBaseURL(), job.getApiURL());
                    } else {
                        log.info(response.getBody());
                    }
                },
                new CronTrigger(job.getCron(), TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()))
        );
        schedulerMap.put(job.getJobName() + "-" + System.currentTimeMillis(), job);
    }
}
