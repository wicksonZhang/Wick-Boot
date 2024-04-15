package com.wick.boot.module.flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Flyway Sync DB 启动类
 *
 * @author ZhangZiHeng
 * @date 2024-04-15
 */
@SpringBootApplication
public class WickFlywayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WickFlywayApplication.class, args);
    }

}
