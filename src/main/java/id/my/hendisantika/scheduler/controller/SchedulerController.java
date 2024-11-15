package id.my.hendisantika.scheduler.controller;

import id.my.hendisantika.scheduler.entity.Job;
import id.my.hendisantika.scheduler.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-scheduler
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/11/24
 * Time: 11.08
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World! " + LocalDateTime.now();
    }


    @PostMapping("/hello")
    public String sayHelloWithName(@RequestBody Job job) {
        return "Hello " + job.getJobName();
    }

    @PostMapping("/scheduleIt")
    public void scheduleGetJob(@RequestBody Job job) {
        if (null != job) {
            schedulerService.scheduleGetJob(job);
        }
    }
}
