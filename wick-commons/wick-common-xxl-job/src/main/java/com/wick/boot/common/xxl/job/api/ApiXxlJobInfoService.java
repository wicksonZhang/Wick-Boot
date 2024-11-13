package com.wick.boot.common.xxl.job.api;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.xxl.job.interceptor.CookieInterceptor;
import com.wick.boot.common.xxl.job.model.vo.jobinfo.XxlJobInfoQueryVO;

import java.util.Map;

/**
 * 定时任务管理-API
 *
 * @author Wickson
 * @date 2024-11-13
 */
@BaseRequest(
        baseURL = "${baseUrl}",
        interceptor = CookieInterceptor.class,
        contentType = "application/x-www-form-urlencoded;charset=UTF-8"
)
public interface ApiXxlJobInfoService {

    /**
     * 分页查询_定时任务管理分页查询
     *
     * @param xxlJobInfoQueryVO 查询条件
     * @return 分页 Map 集合
     */
    @Post(url = "/jobinfo/pageList")
    ForestResponse<Map<String, Object>> getMonitorJobPage(@Body XxlJobInfoQueryVO xxlJobInfoQueryVO);

}
