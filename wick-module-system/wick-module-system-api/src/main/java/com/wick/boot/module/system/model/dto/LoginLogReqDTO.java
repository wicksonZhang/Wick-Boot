package com.wick.boot.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 登录日志创建 Request DTO
 *
 * @author Wickson
 * @date 2024-06-04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LoginLogReqDTO对象", description = "后台管理-登录日志信息")
public class LoginLogReqDTO {

    /**
     * 日志类型
     */
    @NotNull(message = "日志类型不能为空")
    private Integer logType;

    /**
     * 链路追踪编号
     */
    private String traceId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 用户账号
     * <p>
     * 不再强制校验 username 非空，因为 Member 社交登录时，此时暂时没有 username(mobile）！
     */
    private String username;

    /**
     * 登录结果
     */
    @NotNull(message = "登录结果不能为空")
    private Integer result;

    /**
     * 用户 IP
     */
    @NotEmpty(message = "用户 IP 不能为空")
    private String userIp;

    /**
     * 浏览器 UserAgent
     * <p>
     * 允许空，原因：Job 过期登出时，是无法传递 UserAgent 的
     */
    private String userAgent;

}
