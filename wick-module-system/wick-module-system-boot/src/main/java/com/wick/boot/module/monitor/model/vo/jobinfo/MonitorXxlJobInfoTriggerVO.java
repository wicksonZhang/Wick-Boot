package com.wick.boot.module.monitor.model.vo.jobinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Wickson
 * @date 2024-11-15
 */
@Setter
@Getter
@ApiModel(value = "MonitorXxlJobInfoTriggerVO", description = "定时任务管理执行Trigger参数")
public class MonitorXxlJobInfoTriggerVO {

    /**
     * 定时任务编号
     */
    @ApiModelProperty(value = "定时任务编号", required = true, example = "1")
    @NotNull(message = "定时任务编号不能为空")
    private Integer id;

    /**
     * 执行器任务参数
     */
    @ApiModelProperty(value = "执行器任务参数")
    private String executorParam;

    /**
     * 执行器地址列表，多地址逗号分隔
     */
    @ApiModelProperty(value = "执行器地址列表")
    private String addressList;

}
