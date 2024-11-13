package com.wick.boot.module.monitor.model.dto.jobinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务管理-DTO
 *
 * @author Wickson
 * @date 2024-11-13 10:24
 */
@Getter
@Setter
@ApiModel(value = "MonitorXxlJobInfoDTO对象", description = "定时任务管理视图DTO")
public class MonitorXxlJobInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务编号")
    private Integer id;

    @ApiModelProperty(value = "任务描述")
    private String jobDesc;

    @ApiModelProperty(value = "添加时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "负责人")
    private String author;

    @ApiModelProperty(value = "调度类型")
    private String scheduleType;

    @ApiModelProperty(value = "运行模式")
    private String executorHandler;

    @ApiModelProperty(value = "调度状态(0-停止，1-运行)")
    private Integer triggerStatus;

    @ApiModelProperty(value = "上次调度时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime triggerLastTime;

    @ApiModelProperty(value = "下次调度时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime triggerNextTime;

}