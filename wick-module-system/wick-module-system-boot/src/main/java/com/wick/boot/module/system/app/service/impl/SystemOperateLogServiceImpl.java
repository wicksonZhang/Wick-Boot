package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wick.boot.module.system.app.service.ISystemOperateLogService;
import com.wick.boot.module.system.convert.SystemOperateLogConvert;
import com.wick.boot.module.system.mapper.ISystemOperateLogMapper;
import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;
import com.wick.boot.module.system.model.entity.SystemOperateLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.wick.boot.module.system.model.entity.SystemOperateLog.JAVA_METHOD_ARGS_MAX_LENGTH;
import static com.wick.boot.module.system.model.entity.SystemOperateLog.RESULT_MAX_LENGTH;

/**
 * 操作日志-服务实现类
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
@Service
public class SystemOperateLogServiceImpl implements ISystemOperateLogService {

    @Resource
    private ISystemOperateLogMapper operateLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        SystemOperateLog logDO = SystemOperateLogConvert.INSTANCE.convert(createReqDTO);
        logDO.setJavaMethodArgs(StrUtil.maxLength(logDO.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
        logDO.setResultData(StrUtil.maxLength(logDO.getResultData(), RESULT_MAX_LENGTH));
        operateLogMapper.insert(logDO);
    }

}
