package com.wick.boot.common.xxl.job.api;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.xxl.job.interceptor.CookieInterceptor;

/**
 * Xxl-Job - 登录
 *
 * @author Wickson
 * @date 2024-11-12
 */
@BaseRequest(baseURL = "${baseUrl}")
public interface ApiXxlJobLoginService {

    @Post(value = "/login?userName={0}&password={1}", interceptor = CookieInterceptor.class)
    ForestResponse<String> login(String userName, String password);

}
