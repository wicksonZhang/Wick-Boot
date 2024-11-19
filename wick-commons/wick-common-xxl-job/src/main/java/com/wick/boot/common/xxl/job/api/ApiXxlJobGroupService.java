package com.wick.boot.common.xxl.job.api;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.xxl.job.interceptor.CookieInterceptor;
import com.wick.boot.common.xxl.job.model.entity.XxlJobGroup;
import com.wick.boot.common.xxl.job.model.vo.jobgroup.XxlJobGroupQueryVO;

/**
 * 执行器管理-API
 *
 * @author Wickson
 * @date 2024-11-11
 */
@BaseRequest(
        baseURL = "${xxlHttpUrl}",
        interceptor = CookieInterceptor.class,
        contentType = "application/x-www-form-urlencoded;charset=UTF-8"
)
public interface ApiXxlJobGroupService {

    /**
     * 分页查询_执行器管理分页查询
     *
     * @param xxlJobGroupQueryVO 查询条件
     * @return 分页 Map 集合
     */
    @Post(url = "/jobgroup/pageList")
    ForestResponse<String> getMonitorJobGroupPage(@Body XxlJobGroupQueryVO xxlJobGroupQueryVO);

    /**
     * 新增_执行器管理
     *
     * @param xxlJobGroup 新增参数
     */
    @Post(url = "/jobgroup/save")
    ForestResponse<String> addMonitorJobGroup(@Body XxlJobGroup xxlJobGroup);

    /**
     * 更新_执行器管理
     *
     * @param xxlJobGroup 更新参数
     */
    @Post(url = "/jobgroup/update")
    ForestResponse<String> updateMonitorJobGroup(@Body XxlJobGroup xxlJobGroup);

    /**
     * 删除_执行器管理
     */
    @Post(url = "/jobgroup/remove")
    void deleteMonitorJobGroup(@Body("id") int id);

}
