package cn.wickson.security.commons.exception;

import cn.wickson.security.commons.result.ResultCode;
import lombok.Getter;

/**
 * 前端用户操作错误异常类
 *
 * @author ZhangZiHeng
 * @date 2023-12-27
 */
@Getter
public class UserOperationException extends RuntimeException {

    /**
     * 异常代码
     */
    private final Integer code;

    /**
     * 异常代码描述
     */
    private final String description;

    /**
     * 构造器：有参数的构造器
     */
    public UserOperationException(final Integer code, final String message) {
        this.code = code;
        this.description = message;
    }

    /**
     * 格式化为字符串
     *
     * @return
     */
    @Override
    public String toString() {
        return "UserOperationException{" + "code=" + code + ", description='" + description + '}';
    }

    /**
     * 获取类实例
     *
     * @param code
     * @return
     */
    public static UserOperationException getInstance(final Integer code, final String message) {
        return new UserOperationException(code, message);
    }

    /**
     * 获取类实例
     *
     * @param resultCode
     * @return
     */
    public static UserOperationException getInstance(final ResultCode resultCode) {
        return new UserOperationException(resultCode.getCode(), resultCode.getMsg());
    }

}





