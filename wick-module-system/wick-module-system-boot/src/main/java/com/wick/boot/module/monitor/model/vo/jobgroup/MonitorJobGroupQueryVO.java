package com.wick.boot.module.monitor.model.vo.jobgroup;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 定时任务调度分页查询参数
 *
 * @author Wickson
 * @date 2024-11-07 15:25
 */
@Setter
@Getter
@ApiModel(value = "MonitorJobGroupQueryVO", description = "定时任务调度分页查询参数")
public class MonitorJobGroupQueryVO extends CommonPageParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "appName")
    private String appName;

    @ApiModelProperty(value = "任务名称")
    private String title;

}
