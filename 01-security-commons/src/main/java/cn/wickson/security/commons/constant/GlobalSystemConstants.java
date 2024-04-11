package cn.wickson.security.commons.constant;

/**
 * 系统管理 - 常量
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
public interface GlobalSystemConstants {

    /**
     * 根部门ID
     */
    Long ROOT_NODE_ID = 0L;

    /**
     * 系统默认密码
     */
    String DEFAULT_USER_PASSWORD = "123456";

    /**
     * 系统 Token 类型
     */
    String TOKEN_TYPE_BEARER = "Bearer ";

    /**
     * Spring Security 角色的命名规范
     */
    String ROLE = "ROLE_";

    /**
     * 超级管理员角色编码
     */
    String ROOT_ROLE_CODE = "ROOT";

}
