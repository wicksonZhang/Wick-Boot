package com.wick.boot.common.core.exception;

import com.wick.boot.common.core.result.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 前端参数错误异常类
 *
 * @author Wickson
 */
@Getter
@Setter
public class ParameterException extends RuntimeException {

    /**
     * 异常代码
     */
    private ResultCode code;

    /**
     * 异常代码描述
     */
    private String description;

    /**
     * 构造器：有参数的构造器
     */
    public ParameterException(final ResultCode code) {
        this.code = code;
        this.description = code.getMsg();
    }

    /**
     * 构造器：有参数的构造器
     */
    public ParameterException(final ResultCode code, final String message) {
        this.code = code;
        this.description = String.format(code.getMsg() + "{%s}", message);
    }

    /**
     * 格式化为字符串
     *
     * @return
     */
    @Override
    public String toString() {
        return "ParamException{" +
                "code=" + code.getCode() +
                ", message='" + description + '\'' +
                '}';
    }

    /**
     * 获取类实例
     *
     * @param code
     * @return
     */
    public static ParameterException getInstance(final ResultCode code) {
        return new ParameterException(code);
    }

    /**
     * 获取类实例
     *
     * @param code
     * @return
     */
    public static ParameterException getInstance(final ResultCode code, final String message) {
        return new ParameterException(code, message);
    }

}
