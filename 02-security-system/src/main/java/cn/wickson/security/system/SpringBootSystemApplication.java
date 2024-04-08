package cn.wickson.security.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统模块启动类
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@SpringBootApplication(scanBasePackages = "cn.wickson.security")
public class SpringBootSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSystemApplication.class, args);
    }

}
