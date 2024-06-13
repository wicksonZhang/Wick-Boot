package com.wick.boot.module.system.api;

import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;

import javax.validation.Valid;

/**
 * 操作日志 API 接口
 *
 * @author ZhangZiHeng
 * @date 2024-06-13
 */
public interface OperateLogApi {

    /**
     * 创建操作日志
     *
     * @param reqDTO 请求
     */
    void createOperateLog(@Valid OperateLogCreateReqDTO reqDTO);
}
