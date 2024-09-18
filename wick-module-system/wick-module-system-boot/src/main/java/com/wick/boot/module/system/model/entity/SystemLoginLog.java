package com.wick.boot.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登录日志表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("system_login_log")
public class SystemLoginLog extends BaseDO {

    /**
     * 日志主键
     */
    private Long id;

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户账号
     * <p>
     * 冗余，因为账号可以变更
     */
    private String userName;

    /**
     * 用户 IP
     */
    private String userIp;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 登录结果
     */
    private Integer result;

    /**
     * 浏览器 UA
     */
    private String userAgent;

    /**
     * 操作系统
     */
    private String os;

}