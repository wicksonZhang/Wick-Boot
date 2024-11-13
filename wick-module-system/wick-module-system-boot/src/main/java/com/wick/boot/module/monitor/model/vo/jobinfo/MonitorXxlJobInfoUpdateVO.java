package com.wick.boot.module.monitor.model.vo.jobinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 定时任务管理更新参数类
 *
 * @author Wickson
 * @date 2024-11-13 10:24
 */
@Setter
@Getter
@ApiModel(value = "MonitorXxlJobInfoUpdateVO对象", description = "定时任务管理更新参数")
public class MonitorXxlJobInfoUpdateVO extends MonitorXxlJobInfoAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", required = true, example = "1")
    @NotNull(message = "主键ID不能为空")
    private Integer id;
}
