package com.wick.boot.module.monitor.model.vo.job;

import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.validator.InEnum;
import com.wick.boot.module.system.enums.monitor.MisfirePolicyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(max = 64, message = "任务名称不能超过64个字符")
    private String jobName;

    @ApiModelProperty(value = "任务组名", required = true)
    @NotBlank(message = "任务组名不能为空")
    private String jobGroup;

    @ApiModelProperty(value = "调用目标字符串", required = true)
    @NotBlank(message = "调用目标字符串不能为空")
    @Size(max = 500, message = "调用目标字符串长度不能超过500个字符")
    private String invokeTarget;

    @ApiModelProperty(value = "cron执行表达式", required = true)
    @NotBlank(message = "cron执行表达式不能为空")
    @Size(max = 255, message = "Cron执行表达式不能超过255个字符")
    private String cronExpression;

    @ApiModelProperty(value = "计划执行错误策略（1-立即执行、2-执行一次、3-放弃执行）", required = true)
    @NotNull(message = "计划执行错误策略（1-立即执行、2-执行一次、3-放弃执行）不能为空")
    @InEnum(value = MisfirePolicyEnum.class, message = "定时任务执行错误策略必须是 {value}")
    private Integer misfirePolicy;

    @ApiModelProperty(value = "是否并发执行（1-允许、0-禁止）", required = true)
    @NotNull(message = "是否并发执行（1-允许、0-禁止）不能为空")
    @InEnum(value = CommonStatusEnum.class, message = "定时任务并发执行必须是 {value}")
    private Integer concurrent;

    @ApiModelProperty(value = "状态（1-启用、0-禁用）", required = true)
    @NotNull(message = "状态（1-启用、0-禁用）不能为空")
    @InEnum(value = CommonStatusEnum.class, message = "定时任务状态必须是 {value}")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
