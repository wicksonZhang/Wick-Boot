package com.wick.boot.common.xxl.job.api;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.xxl.job.interceptor.CookieInterceptor;
import com.wick.boot.common.xxl.job.model.vo.joblog.XxlJobLogQueryVO;

/**
 * Xxl-Job - 调度日志
 *
 * @author Wickson
 * @date 2024-11-15
 */
@BaseRequest(
        baseURL = "${xxlHttpUrl}",
        interceptor = CookieInterceptor.class,
        contentType = "application/x-www-form-urlencoded;charset=UTF-8"
)
public interface ApiXxlJobLogService {


    @Post(url = "/joblog/getJobsByGroup")
    ForestResponse<String> getJobsByGroup(@Body("jobGroup") int jobGroup);

    @Post(url = "/joblog/pageList")
    ForestResponse<String> getPage(@Body XxlJobLogQueryVO queryVO);
}
