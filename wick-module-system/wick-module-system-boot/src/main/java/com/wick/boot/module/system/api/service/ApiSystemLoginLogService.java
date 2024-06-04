package com.wick.boot.module.system.api.service;

import com.wick.boot.module.system.api.ApiSystemLoginLog;
import com.wick.boot.module.system.app.service.ISystemLoginLogService;
import com.wick.boot.module.system.model.dto.LoginLogReqDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统登录日志 Api
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
@Service
public class ApiSystemLoginLogService implements ApiSystemLoginLog {

    @Resource
    private ISystemLoginLogService loginLogService;

    @Override
    public void saveLoginLog(LoginLogReqDTO logReqDTO) {
        loginLogService.createLoginLog(logReqDTO);
    }

}
