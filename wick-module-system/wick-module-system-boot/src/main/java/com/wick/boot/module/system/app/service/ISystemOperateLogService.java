package com.wick.boot.module.system.app.service;

import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;

/**
 * 登录日志-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
public interface ISystemOperateLogService {

    /**
     * 创建操作日志
     *
     * @param createReqDTO 请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

}
