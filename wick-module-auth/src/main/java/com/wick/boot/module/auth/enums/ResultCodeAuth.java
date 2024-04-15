package com.wick.boot.module.auth.enums;


import com.wick.boot.common.core.result.ResultCode;

/**
 * System 错误码枚举类
 * <p>
 * system 系统，使用 1-002-000-000 段
 */
public interface ResultCodeAuth {

    // ======================================== AUTH 模块 1-002-000-000 ========================================
    ResultCode AUTH_CAPTCHA_CODE_ERROR = new ResultCode(1002000001, "验证码错误");
    ResultCode AUTH_USER_PASSWORD_ERROR = new ResultCode(1002000002, "当前用户账号密码错误");
    ResultCode AUTH_USER_STATUS_DISABLE = new ResultCode(1002000003, "当前账号已被停用");



}
