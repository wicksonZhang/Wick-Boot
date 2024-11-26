package com.wick.boot.module.monitor.model.vo.jobgroup;

import com.wick.boot.common.core.validator.InEnum;
import com.wick.boot.module.system.enums.monitor.JobGroupAddressTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 定时任务调度新增参数
 *
 * @author Wickson
 * @date 2024-11-07 15:25
 */
@Getter
@Setter
@ApiModel(value = "MonitorJobGroupAddVO", description = "定时任务调度新增参数")
public class MonitorJobGroupAddVO {

    @ApiModelProperty(value = "执行器AppName", required = true, example = "xxl-job-executor-sample")
    @NotBlank(message = "appName 不能为空")
    @Length(min = 4, max = 64, message = "AppName长度限制为4~64")
    private String appName;

    @ApiModelProperty(value = "执行器名称", required = true, example = "示例执行器")
    @NotBlank(message = "执行器名称不能为空")
    @Length(min = 4, max = 12, message = "执行器名称长度限制为4~12")
    private String title;

    @ApiModelProperty(value = "注册方式(0-自动注册、1-手动录入)", required = true, example = "0")
    @NotNull(message = "注册方式不能为空")
    @InEnum(value = JobGroupAddressTypeEnum.class, message = "注册方式必须是 {value}")
    private Integer addressType;

    @ApiModelProperty(value = "机器地址", example = "192.168.0.1")
    private String addressList;

}
