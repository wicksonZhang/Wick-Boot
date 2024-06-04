package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wick.boot.module.system.app.service.ISystemLoginLogService;
import com.wick.boot.module.system.mapper.ISystemLoginLogMapper;
import com.wick.boot.module.system.model.dto.LoginLogReqDTO;
import com.wick.boot.module.system.model.entity.SystemLoginLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登录日志-服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
@Service
public class SystemLoginLogServiceImpl implements ISystemLoginLogService {

    @Resource
    private ISystemLoginLogMapper loginLogMapper;

    @Override
    public void createLoginLog(LoginLogReqDTO logReqDTO) {
        SystemLoginLog systemLoginLog = new SystemLoginLog();
        BeanUtil.copyProperties(logReqDTO, systemLoginLog);
        this.loginLogMapper.insert(systemLoginLog);
    }
}
