package com.wick.boot.common.xxl.job.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel
@Getter
@Setter
public class XxlJobInfo {

    @ApiModelProperty(value = "主键ID，唯一标识任务信息", position = 1)
    private int id;

    @ApiModelProperty(value = "执行器主键ID，标识任务所属的执行器组", position = 2)
    private int jobGroup;

    @ApiModelProperty(value = "任务描述，简要说明任务的功能或目的", position = 3)
    private String jobDesc;

    @ApiModelProperty(value = "任务添加时间，记录任务信息的创建时间", position = 4)
    private Date addTime;

    @ApiModelProperty(value = "任务更新时间，记录任务信息的最后修改时间", position = 5)
    private Date updateTime;

    @ApiModelProperty(value = "负责人，任务创建者或负责人的名称", position = 6)
    private String author;

    @ApiModelProperty(value = "报警邮件，任务触发报警时通知的邮件地址", position = 7)
    private String alarmEmail;

    @ApiModelProperty(value = "调度类型，指定任务的调度方式（如CRON表达式、简单定时等）", position = 8)
    private String scheduleType;

    @ApiModelProperty(value = "调度配置，调度类型的具体配置（如CRON表达式内容）", position = 9)
    private String scheduleConf;

    @ApiModelProperty(value = "调度过期策略，指定任务在错过执行时的处理策略", position = 10)
    private String misfireStrategy;

    @ApiModelProperty(value = "执行器路由策略，定义任务在执行器中的路由规则", position = 11)
    private String executorRouteStrategy;

    @ApiModelProperty(value = "执行器，任务Handler名称，执行任务的处理函数或类名", position = 12)
    private String executorHandler;

    @ApiModelProperty(value = "执行器，任务参数，传递给任务Handler的参数", position = 13)
    private String executorParam;

    @ApiModelProperty(value = "阻塞处理策略，定义任务执行时的阻塞处理策略", position = 14)
    private String executorBlockStrategy;

    @ApiModelProperty(value = "任务执行超时时间，单位秒，任务超时未完成时的处理时间限制", position = 15)
    private int executorTimeout;

    @ApiModelProperty(value = "失败重试次数，任务执行失败时的重试次数", position = 16)
    private int executorFailRetryCount;

    @ApiModelProperty(value = "GLUE类型，任务执行所用的GLUE语言类型（如Java、Groovy等）", position = 17)
    private String glueType;

    @ApiModelProperty(value = "GLUE源代码，任务执行所用的GLUE脚本内容", position = 18)
    private String glueSource;

    @ApiModelProperty(value = "GLUE备注，GLUE代码的说明或备注", position = 19)
    private String glueRemark;

    @ApiModelProperty(value = "GLUE更新时间，记录GLUE代码的最后更新时间", position = 20)
    private Date glueUpdatetime;

    @ApiModelProperty(value = "子任务ID，多个子任务ID用逗号分隔，表示当前任务的子任务列表", position = 21)
    private String childJobId;

    @ApiModelProperty(value = "调度状态：0-停止，1-运行，表示任务的调度当前状态", position = 22)
    private int triggerStatus;

    @ApiModelProperty(value = "上次调度时间，记录任务上次执行的时间", position = 23)
    private long triggerLastTime;

    @ApiModelProperty(value = "下次调度时间，记录任务下次执行的时间", position = 24)
    private long triggerNextTime;

}