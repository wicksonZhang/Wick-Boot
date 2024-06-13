package com.wick.boot.common.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wick.boot.common.log.entity.OperateLog;
import com.wick.boot.common.log.service.OperateLogFrameworkService;
import com.wick.boot.module.system.api.OperateLogApi;
import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 Framework Service 实现类
 * <p>
 * 基于 {@link OperateLogApi} 实现，记录操作日志
 */
@RequiredArgsConstructor
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService {

    private final OperateLogApi operateLogApi;

    @Override
    @Async
    public void createOperateLog(OperateLog operateLog) {
        OperateLogCreateReqDTO reqDTO = BeanUtil.copyProperties(operateLog, OperateLogCreateReqDTO.class);
        operateLogApi.createOperateLog(reqDTO);
    }

}
