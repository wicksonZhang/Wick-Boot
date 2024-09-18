package com.wick.boot.common.core.utils;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 客户端工具类
 *
 * @author Wickson
 * @date 2024-06-04
 */
@Slf4j
public class ServletUtils {

    /**
     * 获得请求
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 获取浏览器信息
     *
     * @return Google
     */
    public static String getUserAgent() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return getUserAgent(request);
    }

    /**
     * 获取浏览器信息
     *
     * @param request 请求
     * @return ua
     */
    public static String getUserAgent(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return ua != null ? ua : "";
    }

    /**
     * 获取IP地址
     *
     * @return 客户端IP地址
     */
    public static String getClientIP() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        String ip = null;
        try {

            ip = request.getHeader("x-forwarded-for");
            if (checkIp(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (checkIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (checkIp(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (checkIp(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (checkIp(ip)) {
                ip = request.getRemoteAddr();
                if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                    // 根据网卡取本机配置的IP
                    ip = getLocalAddr();
                }
            }
        } catch (Exception e) {
            log.error("IPUtils ERROR, {}" , e.getMessage());
        }

        // 使用代理，则获取第一个IP地址
        if (StrUtil.isNotBlank(ip) && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        return ip;
    }

    /**
     * 检查ip是否正确
     *
     * @param ip ip地址
     * @return true or false
     */
    private static boolean checkIp(String ip) {
        String unknown = "unknown";
        return StrUtil.isEmpty(ip) || unknown.equalsIgnoreCase(ip);
    }

    /**
     * 获取本机的IP地址
     *
     * @return 本机IP地址
     */
    private static String getLocalAddr() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("InetAddress.getLocalHost()-error, {}" , e.getMessage());
        }
        return null;
    }

    /**
     * 获取真实地址
     *
     * @param clientIP IP地址
     * @return ip对应得地址
     */
    public static String getRealAddressByIP(String clientIP) {
        String loginLocation = "未知IP";
        if (StrUtil.isBlank(clientIP)) {
            return loginLocation;
        }

        // 内网ip
        if (NetUtil.isInnerIP(clientIP)) {
            // 根据网卡取本机配置的IP
            return "内网ip";
        }

        String region = IPUtils.getRegion(clientIP);
        // 中国|0|四川省|成都市|电信 解析省和市
        if (StrUtil.isNotBlank(region)) {
            String[] regionArray = region.split("\\|");
            if (regionArray.length > 2) {
                loginLocation = regionArray[2] + " " + regionArray[3];
            }
        }
        return loginLocation;
    }

}
