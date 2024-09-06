package com.wick.boot.module.system.api;

import com.wick.boot.module.system.model.dto.LoginLogReqDTO;

import javax.validation.Valid;

/**
 * 系统模块-登录日志
 *
 * @author Wickson
 * @date 2024-06-04
 */
public interface ApiSystemLoginLog {

    /**
     * 保存日志记录
     *
     * @param logReqDTO 日志记录
     */
    void saveLoginLog(@Valid LoginLogReqDTO logReqDTO);

}
