package com.wick.boot.common.core.constant;

/**
 * 系统管理 - 常量
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
public interface GlobalConstants {

    /**
     * 根部门ID
     */
    Long ROOT_NODE_ID = 0L;

    /**
     * 系统默认密码
     */
    String DEFAULT_USER_PASSWORD = "123456";

    /**
     * 超级管理员角色编码
     */
    String ROOT_ROLE_CODE = "ROOT";

    /**
     * 用户过期时间
     */
    Long EXPIRATION_TIME = 7200L;

    /**
     * Token 前缀
     */
    String TOKEN_TYPE_BEARER = "Bearer ";

}
