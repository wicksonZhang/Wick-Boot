package com.wick.boot.common.core.result;

import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 前端统一返回结果类
 *
 * @author Wickson
 * @date 2023-12-27
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultUtil<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态码描述
     */
    private String msg;

    /**
     * 数据返回主体
     */
    private T data;

    /**
     * 操作成功-无数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> success() {
        return getInstance(GlobalResultCodeConstants.SUCCESS);
    }

    /**
     * 操作成功-有数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> success(T data) {
        return getInstance(GlobalResultCodeConstants.SUCCESS, data);
    }

    /**
     * 操作成功-有数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> success(Integer code, String msg) {
        return getInstance(code, msg);
    }


    /**
     * 操作成功-无数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> failure() {
        return getInstance(GlobalResultCodeConstants.FAIL);
    }

    /**
     * 操作成功-有数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> failure(T data) {
        return getInstance(GlobalResultCodeConstants.FAIL, data);
    }

    /**
     * 操作失败-无数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> failure(ResultCode code) {
        return getInstance(code);
    }

    /**
     * 操作失败-有数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> failure(ResultCode code, String msg) {
        return getInstance(code, msg);
    }

    /**
     * 操作失败-有数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> failure(ResultCode code, T data) {
        return getInstance(code, data);
    }

    /**
     * 操作成功-有数据主体返回
     *
     * @return 返回Result类实例
     */
    public static <T> ResultUtil<T> failure(Integer code, String msg) {
        return getInstance(code, msg);
    }

    /**
     * 获取Result类实例
     *
     * @return 返回Result类实例
     */
    private static <T> ResultUtil<T> getInstance(ResultCode code) {
        return getInstance(code.getCode(), code.getMsg(), null);
    }

    /**
     * 获取Result类实例
     *
     * @return 返回Result类实例
     */
    private static <T> ResultUtil<T> getInstance(ResultCode code, String msg) {
        return getInstance(code.getCode(), msg, null);
    }

    /**
     * 获取Result类实例
     *
     * @return 返回Result类实例
     */
    private static <T> ResultUtil<T> getInstance(ResultCode code, T data) {
        return getInstance(code.getCode(), code.getMsg(), data);
    }

    /**
     * 获取空值Result类实例
     *
     * @param code
     * @param msg
     * @return
     */
    private static <T> ResultUtil<T> getInstance(Integer code, String msg) {
        ResultUtil<T> result = ResultUtil.getInstance();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 获取空值Result类实例
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    private static <T> ResultUtil<T> getInstance(Integer code, String msg, T data) {
        ResultUtil<T> result = ResultUtil.getInstance();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 获取空值Result类实例
     *
     * @return
     */
    private static <T> ResultUtil<T> getInstance() {
        return new ResultUtil<T>();
    }

}
