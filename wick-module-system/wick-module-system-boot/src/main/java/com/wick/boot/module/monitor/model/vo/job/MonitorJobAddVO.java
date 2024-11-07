package com.wick.boot.module.monitor.model.vo.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 定时任务调度新增参数
 *
 * @author Wickson
 * @date 2024-11-07 15:25
 */
@Getter
@Setter
@ApiModel(value = "MonitorJobAddVO", description = "定时任务调度新增参数")
public class MonitorJobAddVO {

    @ApiModelProperty(value = "任务名称", required = true)
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @ApiModelProperty(value = "任务组名", required = true)
    @NotBlank(message = "任务组名不能为空")
    private String jobGroup;

    @ApiModelProperty(value = "调用目标字符串", required = true)
    @NotBlank(message = "调用目标字符串不能为空")
    private String invokeTarget;

    @ApiModelProperty(value = "cron执行表达式", required = true)
    @NotBlank(message = "cron执行表达式不能为空")
    private String cronExpression;

    @ApiModelProperty(value = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）", required = true)
    @NotNull(message = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）不能为空")
    private Integer misfirePolicy;

    @ApiModelProperty(value = "是否并发执行（0允许 1禁止）", required = true)
    @NotNull(message = "是否并发执行（0允许 1禁止）不能为空")
    private Integer concurrent;

    @ApiModelProperty(value = "状态（1正常 0停用）", required = true)
    @NotNull(message = "状态（1正常 0停用）不能为空")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
