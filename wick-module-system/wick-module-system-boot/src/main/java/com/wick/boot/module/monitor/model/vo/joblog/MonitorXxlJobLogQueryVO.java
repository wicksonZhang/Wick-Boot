package com.wick.boot.module.monitor.model.vo.joblog;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 调度日志分页查询参数
 *
 * @author Wickson
 * @date 2024-11-15 14:22
 */
@Setter
@Getter
@ApiModel(value = "MonitorXxlJobLogQueryVO", description = "调度日志分页查询参数")
public class MonitorXxlJobLogQueryVO extends CommonPageParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "执行器编号")
    private Integer jobGroup;

    @ApiModelProperty(value = "任务编号")
    private Integer jobId;

    @ApiModelProperty(value = "日志状态")
    private Integer logStatus;

    @ApiModelProperty(value = "调度时间", example = "[2024-06-01 00:00:00, 2024-06-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] triggerTime;

}