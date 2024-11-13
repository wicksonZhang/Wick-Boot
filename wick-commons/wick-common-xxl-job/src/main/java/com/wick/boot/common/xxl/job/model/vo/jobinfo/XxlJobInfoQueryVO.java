package com.wick.boot.common.xxl.job.model.vo.jobinfo;

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
public class XxlJobInfoQueryVO {

    /**
     * 开始页
     */
    private Integer start = 0;

    /**
     * 每页条数
     */
    private Integer length = 10;

    /**
     * 执行器
     */
    private Integer jobGroup;

    /**
     * 调度状态
     */
    private Integer triggerStatus;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 负责人
     */
    private String author;

}
