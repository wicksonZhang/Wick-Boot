package com.wick.boot.module.monitor.model.vo.jobinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 定时任务管理新增参数
 *
 * @author Wickson
 * @date 2024-11-13 10:24
 */
@Getter
@Setter
@ApiModel(value = "MonitorXxlJobInfoAddVO", description = "定时任务管理新增参数")
public class MonitorXxlJobInfoAddVO {

    @ApiModelProperty(value = "执行器主键ID", required = true)
    @NotNull(message = "执行器主键ID不能为空")
    private Integer jobGroup;

    @ApiModelProperty(value = "任务描述", required = true)
    @NotBlank(message = "任务描述不能为空")
    private String jobDesc;

    @ApiModelProperty(value = "添加时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "负责人", required = true)
    @NotBlank(message = "负责人不能为空")
    private String author;

    @ApiModelProperty(value = "报警邮件")
    private String alarmEmail;

    @ApiModelProperty(value = "调度类型", required = true)
    @NotBlank(message = "调度类型不能为空")
    private String scheduleType;

    @ApiModelProperty(value = "调度配置，值含义取决于调度类型")
    private String scheduleConf;

    @ApiModelProperty(value = "调度过期策略")
    private String misfireStrategy;

    @ApiModelProperty(value = "执行器路由策略")
    private String executorRouteStrategy;

    @ApiModelProperty(value = "执行器任务handler")
    private String executorHandler;

    @ApiModelProperty(value = "执行器任务参数")
    private String executorParam;

    @ApiModelProperty(value = "阻塞处理策略")
    private String executorBlockStrategy;

    @ApiModelProperty(value = "任务执行超时时间，单位秒")
    private Integer executorTimeout;

    @ApiModelProperty(value = "失败重试次数")
    private Integer executorFailRetryCount;

    @ApiModelProperty(value = "GLUE类型")
    private String glueType;

    @ApiModelProperty(value = "GLUE源代码")
    private String glueSource;

    @ApiModelProperty(value = "GLUE备注")
    private String glueRemark;

    @ApiModelProperty(value = "GLUE更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime glueUpdatetime;

    @ApiModelProperty(value = "子任务ID，多个逗号分隔")
    private String childJobid;

    @ApiModelProperty(value = "调度状态：0-停止，1-运行")
    private Integer triggerStatus;

    @ApiModelProperty(value = "上次调度时间")
    private Long triggerLastTime;

    @ApiModelProperty(value = "下次调度时间")
    private Long triggerNextTime;

}


