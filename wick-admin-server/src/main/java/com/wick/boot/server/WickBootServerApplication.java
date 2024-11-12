package com.wick.boot.server;

import com.dtflys.forest.springboot.annotation.ForestScan;
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
@ForestScan(basePackages = "com.wick.boot.common.xxl.job.api")
public class  WickBootServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WickBootServerApplication.class, args);
    }

}
