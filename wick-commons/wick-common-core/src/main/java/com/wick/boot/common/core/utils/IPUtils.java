package com.wick.boot.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * IP工具类
 * <p>
 * 获取客户端IP地址和IP地址对应的地理位置信息
 * <p>
 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
 * </p>
 *
 * @author Ray
 * @since 2.10.0
 */
@Slf4j
@Component
public class IPUtils {

    private static final String DB_PATH = "/data/ip2region.xdb";
    private static Searcher searcher;

    @PostConstruct
    public void init() {
        try {
            // 从类路径加载资源文件
            InputStream inputStream = getClass().getResourceAsStream(DB_PATH);
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + DB_PATH);
            }

            // 将资源文件复制到临时文件
            Path tempDbPath = Files.createTempFile("ip2region", ".xdb");
            Files.copy(inputStream, tempDbPath, StandardCopyOption.REPLACE_EXISTING);

            // 使用临时文件初始化 Searcher 对象
            searcher = Searcher.newWithFileOnly(tempDbPath.toString());
        } catch (Exception e) {
            log.error("IpRegionUtil initialization ERROR, {}", e.getMessage());
        }
    }

    /**
     * 根据IP地址获取地理位置信息
     *
     * @param ip IP地址
     * @return 地理位置信息
     */
    public static String getRegion(String ip) {
        if (searcher == null) {
            log.error("Searcher is not initialized");
            return null;
        }

        try {
            return searcher.search(ip);
        } catch (Exception e) {
            log.error("IpRegionUtil ERROR, {}", e.getMessage());
            return null;
        }
    }
}
