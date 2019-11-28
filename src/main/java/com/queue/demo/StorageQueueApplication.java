package com.queue.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vip.justlive.common.web.base.CodedExceptionResolver;

@EnableTransactionManagement
@SpringBootApplication()
@MapperScan("com.queue.demo.dao.*")
@EnableScheduling
public class StorageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageQueueApplication.class, args);
    }

    @Bean
    CodedExceptionResolver codedExceptionResolver() {
        return new CodedExceptionResolver();
    }
}
