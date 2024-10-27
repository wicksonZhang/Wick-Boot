package com.wick.boot.module.monitor.model.dto.online;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 在线用户-DTO
 *
 * @author Wickson
 * @date 2024-10-25
 */
@Getter
@Setter
@ToString
@ApiModel(value = "SystemOperateLogDTO对象", description = "操作日志记录视图DTO")
public class MonitorOnlineDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会话编号")
    private String sessionId;

    @ApiModelProperty(value = "登录用户")
    private String username;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "登录ip")
    private String loginIp;

    @ApiModelProperty(value = "登录地址")
    private String loginAddress;

    @ApiModelProperty(value = "登录时间")
    private String loginDate;

}
