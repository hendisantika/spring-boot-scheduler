package id.my.hendisantika.scheduler.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
@Component
public class WebClientBean {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
