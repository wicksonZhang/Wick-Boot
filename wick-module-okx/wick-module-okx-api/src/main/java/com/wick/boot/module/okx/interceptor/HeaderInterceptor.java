package com.wick.boot.module.okx.interceptor;

import cn.hutool.core.util.StrUtil;
import com.dtflys.forest.http.ForestHeaderMap;
import com.dtflys.forest.http.ForestProxy;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.Interceptor;
import com.wick.boot.module.okx.api.api.config.ApiApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

/**
 * OKX API 请求拦截器
 * 用于处理 API 请求的签名认证和请求头设置
 */
@Slf4j
@Component
public class HeaderInterceptor<T> implements Interceptor<T> {

    // HMAC-SHA256 加密算法常量
    private static final String HMAC_SHA256 = "HmacSHA256";
    // OKX API 基础URL
    private static final String BASE_URL = "https://www.okx.com";

    // 代理服务器配置，从配置文件中注入
    @Value("${proxy.host:127.0.0.1}")
    private String proxyHost;

    @Value("${proxy.port:7890}")
    private Integer proxyPort;

    @Resource
    private ApiApiConfig apiApiConfig;

    /**
     * 请求执行前的处理方法
     * 主要负责设置请求头和配置代理
     */
    @Override
    public boolean beforeExecute(ForestRequest request) {
        try {
            // 获取 Api配置信息
            Map<String, Object> config = apiApiConfig.getApiConfig();
            // 设置请求头
            setRequestHeaders(request, config);
            // 配置代理
            configureProxy(request);

            return Interceptor.super.beforeExecute(request);
        } catch (Exception e) {
            log.error("请求预处理失败", e);
            throw new RuntimeException("Request preprocessing failed", e);
        }
    }

    /**
     * 设置 OKX API 需要的请求头
     * 包括 API key、时间戳、密码短语和签名等
     */
    private void setRequestHeaders(ForestRequest request, Map<String, Object> config) {
        try {
            String timestamp = Instant.now().toString();
            String apiKey = getConfigValue(config, "api_key");
            String secretKey = getConfigValue(config, "secret_key");
            String passphrase = getConfigValue(config, "passphrase");

            String sign = generateSignature(request, timestamp, secretKey);

            ForestHeaderMap headers = request.getHeaders();
            headers.setHeader("OK-ACCESS-KEY", apiKey);
            headers.setHeader("OK-ACCESS-TIMESTAMP", timestamp);
            headers.setHeader("OK-ACCESS-PASSPHRASE", passphrase);
            headers.setHeader("OK-ACCESS-SIGN", sign);
        } catch (Exception e) {
            log.error("设置请求头失败", e);
            throw new RuntimeException("Failed to set request headers", e);
        }
    }

    /**
     * 从配置中获取指定键的值
     * 如果值不存在则抛出异常
     */
    private String getConfigValue(Map<String, Object> config, String key) {
        return Optional.ofNullable(config.get(key))
                .map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("缺少必要的配置项: " + key));
    }

    /**
     * 配置 HTTP 代理
     * 如果代理主机和端口都有效则设置代理
     */
    private void configureProxy(ForestRequest request) {
        if (StrUtil.isNotBlank(proxyHost) && proxyPort != null) {
            request.proxy(ForestProxy.http(proxyHost, proxyPort));
        }
    }

    /**
     * 生成 API 请求签名
     * 签名规则：timestamp + method + requestPath + body
     */
    private String generateSignature(ForestRequest request, String timestamp, String secretKey) throws Exception {
        // 获取请求方法（转换为大写）
        String method = request.getType().toString().toUpperCase();
        // 获取请求路径（移除基础URL）
        String requestPath = request.getUrl().replace(BASE_URL, "");

        // 对于GET请求，如果有查询参数，将其附加到路径后
        if ("GET".equalsIgnoreCase(method) && StrUtil.isNotBlank(request.getQueryString())) {
            requestPath += "?" + request.getQueryString();
        }

        // 获取请求体，POST请求才有请求体
        String body = "POST".equalsIgnoreCase(method) ?
                Optional.ofNullable(request.getBody()).map(Object::toString).orElse("") : "";

        // 组合待签名字符串
        String preHashString = timestamp + method + requestPath + body;
        return calculateHmacSHA256(preHashString, secretKey);
    }

    /**
     * 计算 HMAC-SHA256 签名
     * 使用 Base64 编码返回最终签名
     */
    private String calculateHmacSHA256(String data, String secretKey) throws Exception {
        try {
            // 创建加密密钥
            SecretKeySpec signingKey = new SecretKeySpec(
                    secretKey.getBytes(StandardCharsets.UTF_8),
                    HMAC_SHA256
            );

            // 初始化 HMAC-SHA256 算法
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(signingKey);

            // 计算签名并进行 Base64 编码
            byte[] signatureBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            log.error("计算签名失败: {}", data, e);
            throw e;
        }
    }
}