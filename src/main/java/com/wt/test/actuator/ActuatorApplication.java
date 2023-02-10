package com.wt.test.actuator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sun.misc.Signal;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableRetry
@EnableTransactionManagement
@MapperScan("com.wt.test.actuator.mapper")
public class ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

}
