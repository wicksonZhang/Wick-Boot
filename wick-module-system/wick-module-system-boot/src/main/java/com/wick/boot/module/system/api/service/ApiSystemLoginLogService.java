package com.wick.boot.module.system.api;

import com.wick.boot.module.system.service.SystemLoginLogService;
import com.wick.boot.module.system.model.dto.LoginLogReqDTO;
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
    private SystemLoginLogService loginLogService;

    @Override
    public void saveLoginLog(LoginLogReqDTO logReqDTO) {
        loginLogService.createLoginLog(logReqDTO);
    }

}
