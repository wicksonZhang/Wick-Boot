package com.wick.boot.common.xxl.job.model.vo.jobinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class XxlJobInfoVO {

    @ApiModelProperty(value = "主键ID", position = 1)
    private int id;

    @ApiModelProperty(value = "执行器主键ID", position = 2)
    private int jobGroup;

    @ApiModelProperty(hidden = true)
    private String jobDesc;

    @ApiModelProperty(value = "负责人", position = 4)
    private String author;

    @ApiModelProperty(value = "报警邮件", position = 5)
    private String alarmEmail;

    @ApiModelProperty(value = "调度类型", position = 6)
    private String scheduleType;

    @ApiModelProperty(hidden = true)
    private String scheduleConf;

    @ApiModelProperty(value = "执行器，任务Handler名称", position = 8)
    private String executorHandler;

    @ApiModelProperty(value = "执行器，任务参数", position = 9)
    private String executorParam;

    @ApiModelProperty(value = "执行器路由策略", position = 10)
    private String executorRouteStrategy;

    @ApiModelProperty(value = "调度过期策略", position = 11)
    private String misfireStrategy;

    @ApiModelProperty(value = "阻塞处理策略", position = 12)
    private String executorBlockStrategy;

    @ApiModelProperty(value = "任务执行超时时间，单位秒", position = 13)
    private int executorTimeout;

    @ApiModelProperty(value = "失败重试次数", position = 14)
    private int executorFailRetryCount;

    @ApiModelProperty(value = "GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum", position = 15)
    private String glueType;

    @ApiModelProperty(value = "子任务ID，多个逗号分隔", position = 16)
    private String childJobId;

    @ApiModelProperty(value = "GLUE源代码", position = 17)
    private String glueSource;

    @ApiModelProperty(value = "GLUE备注", position = 18)
    private String glueRemark;

}