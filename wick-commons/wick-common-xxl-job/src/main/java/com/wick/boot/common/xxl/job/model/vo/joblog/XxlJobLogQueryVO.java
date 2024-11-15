package com.wick.boot.common.xxl.job.model.vo.joblog;

import lombok.Getter;
import lombok.Setter;

/**
 * 定时任务管理-查询VO
 *
 * @author Wickson
 * @date 2024-11-13
 */
@Setter
@Getter
public class XxlJobLogQueryVO {

    /**
     * 开始页
     */
    private Integer start = 0;

    /**
     * 每页条数
     */
    private Integer length = 10;

    /**
     * 执行器编号
     */
    private Integer jobGroup;

    /**
     * 任务编号
     */
    private Integer jobId;

    /**
     * 日志状态
     */
    private Integer logStatus;

    /**
     * 调度时间
     */
    private String filterTime;

}
