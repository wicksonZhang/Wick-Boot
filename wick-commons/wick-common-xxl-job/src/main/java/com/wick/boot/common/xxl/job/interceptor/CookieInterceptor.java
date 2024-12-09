package com.wick.boot.common.xxl.job.interceptor;

import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestCookie;
import com.dtflys.forest.http.ForestCookies;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理Cookie的拦截器
 * @author Wickson
 */
@Slf4j
public class CookieInterceptor<T> implements Interceptor<T> {

    /**
     * Cookie在本地存储的缓存
     */
    private final Map<String, List<ForestCookie>> cookieCache = new ConcurrentHashMap<>();

    /**
     * 在请求响应成功后，需要保存Cookie时调用该方法
     *
     * @param request Forest请求对象
     * @param cookies Cookie集合，通过响应返回的Cookie都从该集合获取
     */
    @Override
    public void onSaveCookie(ForestRequest request, ForestCookies cookies) {
        // 获取请求URI的主机名
        String host = request.getURI().getHost();
        // 将从服务端获得的Cookie列表放入缓存，主机名作为Key
        cookieCache.put(host, cookies.allCookies());
    }

    /**
     * 在发送请求前，需要加载Cookie时调用该方法
     *
     * @param request Forest请求对象
     * @param cookies Cookie集合, 需要通过请求发送的Cookie都添加到该集合
     */
    @Override
    public void onLoadCookie(ForestRequest request, ForestCookies cookies) {
        // 获取请求URI的主机名
        String host = request.getURI().getHost();
        // 从缓存中获取之前获得的Cookie列表，主机名作为Key
        List<ForestCookie> cookieList = cookieCache.get(host);
        // 将缓存中的Cookie列表添加到请求Cookie列表中，准备发送到服务端
        // 默认情况下，只有符合条件 (和请求同域名、同URL路径、未过期) 的 Cookie 才能被添加到请求中
        cookies.addAllCookies(cookieList);
    }

    @Override
    public void onError(ForestRuntimeException ex, ForestRequest request, ForestResponse response) {
        log.info("onError --> {}", ex.getMessage());
    }

    @Override
    public void onSuccess(Object data, ForestRequest request, ForestResponse response) {
        log.info("onSuccess --> {}", data);
    }
}
