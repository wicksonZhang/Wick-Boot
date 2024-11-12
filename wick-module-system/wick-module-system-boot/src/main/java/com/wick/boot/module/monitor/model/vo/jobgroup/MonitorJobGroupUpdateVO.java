package com.wick.boot.module.monitor.model.vo.jobgroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 定时任务调度更新参数类
 *
 * @author Wickson
 * @date 2024-11-07 15:25
 */
@Setter
@Getter
@ApiModel(value = "MonitorJobUpdateVO对象", description = "定时任务调度更新参数")
public class MonitorJobGroupUpdateVO extends MonitorJobGroupAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", required = true, example = "1")
    @NotNull(message = "主键ID不能为空")
    private Long id;
}
