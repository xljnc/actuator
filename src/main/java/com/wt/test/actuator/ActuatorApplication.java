package com.wt.test.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActuatorApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(ActuatorApplication.class, args);
        Thread daemon = new Thread(()->{
            while(true) {
                System.out.println("ActuatorApplication started.");
            }
        });
        daemon.setDaemon(true);
        daemon.start();
    }

}
