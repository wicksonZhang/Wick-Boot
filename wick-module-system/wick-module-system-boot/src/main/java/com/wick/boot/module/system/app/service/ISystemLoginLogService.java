package com.wick.boot.module.system.app.service;

import com.wick.boot.module.system.model.dto.LoginLogReqDTO;

/**
 * 登录日志-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
public interface ISystemLoginLogService {

    /**
     * 创建登录日志
     *
     * @param logReqDTO 登录日志DTO
     */
    void createLoginLog(LoginLogReqDTO logReqDTO);
}
