package cn.wickson.security.commons.constant;


import cn.wickson.security.commons.enums.ResultCode;

/**
 * 全局错误码枚举
 * 0-999 系统异常编码保留
 */
public interface GlobalResultCodeConstants {

    ResultCode SUCCESS = new ResultCode(0, "成功");

    ResultCode FAIL = new ResultCode(500, "失败");

    // ===================================== 客户端错误段 =====================================
    ResultCode PARAM_IS_INVALID = new ResultCode(400, "参数无效");
    ResultCode PARAM_IS_BLANK = new ResultCode(402, "参数为空");
    ResultCode PARAM_TYPE_BIND_ERROR = new ResultCode(403, "参数类型错误");
    ResultCode PARAM_REQUEST_DATA_FORMAT_INVALID = new ResultCode(405, "请求参数的数据格式错误");
    ResultCode PARAM_VALIDATED_FAILURE = new ResultCode(406, "参数校验失败");
    ResultCode USER_REQUEST_METHOD_INVALID = new ResultCode(407, "请求方法无效");


    // ===================================== 服务端错误段 =====================================

    // 第三方调用
    ResultCode TRIPARTITE_DATA_RETURN_DATA_EXCEPTION = new ResultCode(800, "服务繁忙，数据获取失败");

    ResultCode SYSTEM_ERROR = new ResultCode(999, "系统繁忙，请稍后重试...");

}