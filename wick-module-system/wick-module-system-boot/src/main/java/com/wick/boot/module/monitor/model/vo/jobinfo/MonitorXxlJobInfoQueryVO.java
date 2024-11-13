package com.wick.boot.module.monitor.model.vo.jobinfo;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 定时任务管理分页查询参数
 *
 * @author Wickson
 * @date 2024-11-13 10:24
 */
@Setter
@Getter
@ApiModel(value = "MonitorXxlJobInfoQueryVO", description = "定时任务管理分页查询参数")
public class MonitorXxlJobInfoQueryVO extends CommonPageParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "执行器主键ID")
    private Integer jobGroup = 1;

    @ApiModelProperty(value = "任务描述")
    private String jobDesc;

    @ApiModelProperty(value = "负责人")
    private String author;

    @ApiModelProperty(value = "执行器任务handler")
    private String executorHandler;

    @ApiModelProperty(value = "调度状态：0-停止，1-运行")
    private Integer triggerStatus = 0;

}
