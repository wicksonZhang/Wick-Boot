package com.wick.boot.common.xxl.job.api;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.xxl.job.interceptor.CookieInterceptor;
import com.wick.boot.common.xxl.job.model.vo.jobgroup.XxlJobGroupQueryVO;
import com.wick.boot.common.xxl.job.model.vo.jobgroup.XxlJobGroupVO;

import java.util.Map;

/**
 * 执行器管理-API
 *
 * @author Wickson
 * @date 2024-11-11
 */
@BaseRequest(baseURL = "${baseUrl}", interceptor = CookieInterceptor.class)
public interface ApiXxlJobGroupService {

    /**
     * 分页查询_执行器管理分页查询
     *
     * @param xxlJobGroupQueryVO 查询条件
     * @return
     */
    @Post(url = "/jobgroup/pageList", contentType = "application/x-www-form-urlencoded")
    ForestResponse<Map<String, Object>> getMonitorJobPage(@Body XxlJobGroupQueryVO xxlJobGroupQueryVO);

    /**
     * 新增_执行器管理
     *
     * @param xxlJobGroupVO 新增参数
     */
    @Post(url = "/jobgroup/save", contentType = "application/x-www-form-urlencoded")
    ForestResponse<String> addMonitorJob(@Body XxlJobGroupVO xxlJobGroupVO);

    /**
     * 更新_执行器管理
     *
     * @param xxlJobGroupVO 更新参数
     */
    @Post(url = "/jobgroup/update", contentType = "application/x-www-form-urlencoded")
    ForestResponse<String> updateMonitorJob(@Body XxlJobGroupVO xxlJobGroupVO);

    /**
     * 删除_执行器管理
     */
    @Post(url = "/jobgroup/remove", contentType = "application/x-www-form-urlencoded")
    void deleteMonitorJob(@Body("id") int id);

}
