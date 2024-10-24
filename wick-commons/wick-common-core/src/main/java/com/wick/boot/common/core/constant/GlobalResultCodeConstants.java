package com.wick.boot.common.core.constant;


import com.wick.boot.common.core.result.ResultCode;

/**
 * 全局错误码枚举
 * 0-999 系统异常编码保留
 */
public interface GlobalResultCodeConstants {

    ResultCode SUCCESS = new ResultCode(0, "操作成功");

    ResultCode FAIL = new ResultCode(500, "操作失败");

    // ===================================== 客户端错误段 =====================================
    ResultCode PARAM_IS_INVALID = new ResultCode(400, "参数无效");
    ResultCode PARAM_IS_BLANK = new ResultCode(400, "参数为空");
    ResultCode PARAM_TYPE_BIND_ERROR = new ResultCode(400, "参数类型错误");
    ResultCode PARAM_REQUEST_DATA_FORMAT_INVALID = new ResultCode(400, "请求参数的数据格式错误");
    ResultCode PARAM_VALIDATED_FAILURE = new ResultCode(400, "参数校验失败");
    ResultCode USER_REQUEST_METHOD_INVALID = new ResultCode(405, "请求方法无效");


    // ===================================== 服务端错误段 =====================================

    ResultCode UNAUTHORIZED = new ResultCode(401, "账号未登录");
    ResultCode ACCESS_UNAUTHORIZED_CODE = new ResultCode(403, "访问未授权");

}