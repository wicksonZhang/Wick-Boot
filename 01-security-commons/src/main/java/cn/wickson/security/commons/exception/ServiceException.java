package cn.wickson.security.commons.exception;

import cn.wickson.security.commons.constant.GlobalResultCodeConstants;
import cn.wickson.security.commons.result.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 三方接口调用异常类
 *
 * @author Wickson
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {

    /**
     * 异常代码
     */
    private Integer code;

    /**
     * 异常代码描述
     */
    private String message;

    /**
     * 异常 resultCode
     */
    private ResultCode resultCode;

    /**
     * 构造器：有参数的构造器
     */
    public ServiceException(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 格式化为字符串
     *
     * @return
     */
    @Override
    public String toString() {
        return "ParamException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * 获取类实例
     *
     * @param message
     * @return
     */
    public static ServiceException fail(final String message) {
        return  getInstance(GlobalResultCodeConstants.FAIL.getCode(), message);
    }

    /**
     * 获取类实例
     *
     * @param code
     * @return
     */
    public static ServiceException getInstance(final Integer code, final String message) {
        return new ServiceException(code, message);
    }

    /**
     * 获取类实例
     *
     * @param resultCode
     * @return
     */
    public static ServiceException getInstance(final ResultCode resultCode) {
        return new ServiceException(resultCode.getCode(), resultCode.getMsg());
    }

}
