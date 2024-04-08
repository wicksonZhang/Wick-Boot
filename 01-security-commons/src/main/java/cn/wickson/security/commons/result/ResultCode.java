package cn.wickson.security.commons.result;

import lombok.Data;

/**
 * 返回客户端状态码对象
 * 全局错误码，占用 [0, 999]
 * 业务异常错误码，占用 [1 000 000 000, +∞)
 * <p>
 * 错误码设计成对象的原因，为未来的 i18 国际化做准备
 *
 * @author ZhangZiHeng
 * @date 2023-12-27
 */
@Data
public class ResultCode {

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码描述信息
     */
    private final String msg;

    public ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}