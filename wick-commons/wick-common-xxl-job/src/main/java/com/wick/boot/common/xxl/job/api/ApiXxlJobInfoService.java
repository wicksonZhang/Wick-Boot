package com.wick.boot.common.xxl.job.api;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.xxl.job.interceptor.CookieInterceptor;
import com.wick.boot.common.xxl.job.model.entity.XxlJobInfo;
import com.wick.boot.common.xxl.job.model.vo.jobinfo.XxlJobInfoQueryVO;

import java.util.Map;

/**
 * 定时任务管理-API
 *
 * @author Wickson
 * @date 2024-11-13
 */
@BaseRequest(
        baseURL = "${xxlHttpUrl}",
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

    /**
     * 新增_定时任务管理
     *
     * @param xxlJobInfo 新增参数
     * @return 结果集
     */
    @Post(url = "/jobinfo/add")
    ForestResponse<String> add(@Body XxlJobInfo xxlJobInfo);

    /**
     * 更新_定时任务管理
     *
     * @param xxlJobInfo 更新参数
     * @return 结果集
     */
    @Post(url = "/jobinfo/update")
    ForestResponse<String> update(@Body XxlJobInfo xxlJobInfo);

    /**
     * 删除_定时任务管理
     */
    @Post(url = "/jobinfo/remove")
    void delete(@Body("id") int id);

    /**
     * 启动_定时任务管理
     */
    @Post(url = "/jobinfo/start")
    ForestResponse<String> start(@Body("id") int id);

    /**
     * 停止_定时任务管理
     */
    @Post(url = "/jobinfo/stop")
    ForestResponse<String> stop(@Body("id") int id);

    /**
     * 执行_定时任务管理
     */
    @Post(url = "/jobinfo/trigger")
    ForestResponse<String> trigger(@Body("id") int id, @Body("executorParam") String executorParam, @Body("addressList") String addressList);
}
