package com.wick.boot.common.log.service;


import com.wick.boot.common.log.entity.OperateLog;

/**
 * 操作日志 Framework Service 接口
 */
public interface OperateLogFrameworkService {

    /**
     * 记录操作日志
     *
     * @param operateLog 操作日志请求
     */
    void createOperateLog(OperateLog operateLog);

}
