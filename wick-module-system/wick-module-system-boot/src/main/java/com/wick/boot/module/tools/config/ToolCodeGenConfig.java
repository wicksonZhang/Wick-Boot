package com.wick.boot.module.tools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 代码自动生成器配置
 *
 * @author ZhangZiHeng
 * @date 2024-07-24
 */
@Component
@ConfigurationProperties(prefix = "tool.code.gen")
@PropertySource(value = {"classpath:generator.yml"}, encoding = "UTF-8")
public class ToolCodeGenConfig {

    /**
     * 作者
     */
    public static String author;

    /**
     * 生成包路径
     */
    public static String packageName;

    public static String getAuthor() {
        return author;
    }

    @Value("${author}")
    public void setAuthor(String author) {
        ToolCodeGenConfig.author = author;
    }

    public static String getPackageName() {
        return packageName;
    }

    @Value("${packageName}")
    public void setPackageName(String packageName) {
        ToolCodeGenConfig.packageName = packageName;
    }

}
