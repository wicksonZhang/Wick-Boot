package com.wick.boot.module.system.api.service;

import com.wick.boot.module.system.api.ApiOperateLog;
import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;
import com.wick.boot.module.system.service.SystemOperateLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 操作日志 API 实现类
 *
 * @author Wickson
 * @date 2024-06-13
 */
@Service
@Validated
public class ApiOperateLogService implements ApiOperateLog {

    @Resource
    private SystemOperateLogService operateLogService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
    }

}
