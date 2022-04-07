package com.example.demo;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@SpringBootApplication
public class DemoApplication {

    ScheduledExecutorService scheduledExecutorService = ExecutorServiceUtil.newScheduledExecutorService();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        while (true){}
    }


    AtomicInteger ai = new AtomicInteger();

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
                scheduledExecutorService.scheduleAtFixedRate(()-> {
                    ListenableFuture<SendResult<String, String>> send =
                            template.send("my_world", "test"+ai.incrementAndGet());
                    send.addCallback(x->{
                        System.out.println("Sent"+ai.get());
                    }, y->{});
                }, 700, 700, MILLISECONDS);
        };
    };
}
