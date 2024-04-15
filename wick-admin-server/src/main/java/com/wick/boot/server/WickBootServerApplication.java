package com.wick.boot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Wick Boot 启动类
 *
 * @author ZhangZiHeng
 * @date 2024-04-13
 */
@SpringBootApplication(scanBasePackages = {"com.wick.boot.module"})
public class WickBootServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WickBootServerApplication.class, args);
    }

}
