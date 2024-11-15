package com.wick.boot.module.monitor.model.dto.joblog;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 调度日志-DTO
 *
 * @author Wickson
 * @date 2024-11-15 14:22
 */
@Getter
@Setter
@ApiModel(value = "MonitorXxlJobLogDTO对象", description = "调度日志视图DTO")
public class MonitorXxlJobLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务编号")
    private Long id;

    @ApiModelProperty(value = "执行器任务handler")
    private String executorHandler;

    @ApiModelProperty(value = "执行器任务参数")
    private String executorParam;

    @ApiModelProperty(value = "调度时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime triggerTime;

    @ApiModelProperty(value = "调度结果")
    private Integer triggerCode;

    @ApiModelProperty(value = "调度日志")
    private String triggerMsg;

    @ApiModelProperty(value = "执行时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime handleTime;

    @ApiModelProperty(value = "执行日志")
    private String handleMsg;

}