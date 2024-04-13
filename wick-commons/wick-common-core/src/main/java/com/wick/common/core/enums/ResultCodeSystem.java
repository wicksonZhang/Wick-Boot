package com.wick.common.core.enums;


import com.wick.common.core.result.ResultCode;

/**
 * System 错误码枚举类
 * <p>
 * system 系统，使用 1-002-000-000 段
 */
public interface ResultCodeSystem {

    // ======================================== AUTH 模块 1-002-000-000 ========================================
    ResultCode AUTH_CAPTCHA_CODE_ERROR = new ResultCode(1002000001, "验证码错误");
    ResultCode AUTH_USER_PASSWORD_ERROR = new ResultCode(1002000002, "当前用户账号密码错误");
    ResultCode AUTH_USER_STATUS_DISABLE = new ResultCode(1002000003, "当前账号已被停用");
    ResultCode AUTH_TOKEN_INVALID = new ResultCode(1002000004, "Token 无效或已过期");

    // ======================================== 用户模块 1-002-001-000 ========================================
    ResultCode USER_USERNAME_EXISTS = new ResultCode(1002001000, "用户账号已经存在");

    ResultCode USER_MOBILE_EXISTS = new ResultCode(1002001001, "手机号已经存在");

    ResultCode USER_EMAIL_EXISTS = new ResultCode(1002001002, "邮箱已经存在");


}
