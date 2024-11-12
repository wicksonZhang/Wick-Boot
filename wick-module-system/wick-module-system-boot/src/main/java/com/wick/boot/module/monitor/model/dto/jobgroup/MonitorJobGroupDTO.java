package com.wick.boot.module.monitor.model.dto.jobgroup;

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
public class MonitorJobGroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务编号")
    private Long id;

    @ApiModelProperty(value = "AppName")
    private String appName;

    @ApiModelProperty(value = "执行器名称")
    private String title;

    @ApiModelProperty(value = "注册方式")
    private String addressType;

    @ApiModelProperty(value = "OnLine 机器地址")
    private String addressList;

}


