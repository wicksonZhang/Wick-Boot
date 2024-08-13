package com.wick.boot.module.tools.config;

import cn.hutool.core.io.file.FileNameUtil;
import com.wick.boot.common.core.factory.YamlPropertyLoaderFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 代码自动生成器配置
 *
 * @author ZhangZiHeng
 * @date 2024-07-24
 */
@Data
@Component
@ConfigurationProperties(prefix = "generator")
@PropertySource(value = {"classpath:generator.yml"}, encoding = "UTF-8", factory = YamlPropertyLoaderFactory.class)
public class ToolCodeGenConfig {

    /**
     * 作者
     */
    private String author;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 后端应用名
     */
    private String backendAppName;

    /**
     * 前端应用名
     */
    private String frontendAppName;

    /**
     * 模板配置
     */
    private Map<String, TemplateConfig> templateConfigs;


    /**
     * 模板配置
     */
    @Data
    public static class TemplateConfig {

        private String templatePath;

        private String packageName;

        private String extension = FileNameUtil.EXT_JAVA;

    }
}