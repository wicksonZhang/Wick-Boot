package com.wick.boot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Wick Boot 启动类
 *
 * @author Wickson
 * @date 2024-04-13
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.wick.boot"})
public class  WickBootServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WickBootServerApplication.class, args);
    }

}
