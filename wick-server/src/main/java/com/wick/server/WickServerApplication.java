package com.wick.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZhangZiHeng
 * @date 2024-04-13
 */
@SpringBootApplication(scanBasePackages = {"com.wick.server","com.wick.module"})
@MapperScan(basePackages={"com.wick.module.**.mapper.*"})
public class WickServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WickServerApplication.class, args);
    }

}
