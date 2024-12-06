package com.wick.boot.module.auth.enums;


import com.wick.boot.common.core.result.ResultCode;

/**
 * Auth 错误码枚举类: 1-001-001-000
 * <p>
 * 业务异常的错误码区间，解决：解决各模块错误码定义，避免重复，在此只声明不做实际使用
 * 一共 10 位，分成四段
 * 第一段，1 位，类型
 * <span> 1 - 业务级别异常 </span>
 * <span> x - 预留 </span>
 * <p>
 * 第二段，3 位，系统类型
 * <span> 001 - 认证中心 </span>
 * <span> 002 - 系统管理 </span>
 * <p>
 * 第三段，3 位，模块
 * <span> 不限制规则。
 * <span> 一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 * <span> 001 - 认证、授权模块
 * <p>
 * 第四段，3 位，错误码
 * 不限制规则，一般建议，每个模块自增。
 */
public interface ErrorCodeAuth {

    // ======================================== AUTH 模块 1-001-001-000 ========================================
    ResultCode AUTH_CAPTCHA_CODE_ERROR = new ResultCode(1001001001, "验证码错误");
    ResultCode AUTH_USER_PASSWORD_ERROR = new ResultCode(1001001002, "当前用户账号密码错误");
    ResultCode AUTH_USER_STATUS_DISABLE = new ResultCode(1001001003, "当前账号已被停用");

    ResultCode REFRESH_TOKEN_INVALID = new ResultCode(1001001004, "Token 刷新失败");

}
