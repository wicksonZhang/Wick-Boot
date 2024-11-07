package com.wick.boot.module.monitor.model.dto.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 定时任务调度-DTO
 *
 * @author Wickson
 * @date 2024-11-07 16:34
 */
@Getter
@Setter
@ApiModel(value = "MonitorJobDTO对象", description = "定时任务调度视图DTO")
public class MonitorJobDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务编号")
    private Long id;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "调用目标字符串")
    private String invokeTarget;

    @ApiModelProperty(value = "cron执行表达式")
    private String cronExpression;

    @ApiModelProperty(value = "状态（1正常 0停用）")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}


