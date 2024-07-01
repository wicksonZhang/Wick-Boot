package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.app.service.ISystemOperateLogService;
import com.wick.boot.module.system.convert.SystemLoggerConvert;
import com.wick.boot.module.system.convert.SystemOperateLogConvert;
import com.wick.boot.module.system.mapper.ISystemOperateLogMapper;
import com.wick.boot.module.system.mapper.ISystemUserMapper;
import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;
import com.wick.boot.module.system.model.dto.SystemOperateLogDTO;
import com.wick.boot.module.system.model.entity.SystemOperateLog;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.vo.logger.operate.QueryOperateLogPageReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Resource
    private ISystemUserMapper systemUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)

    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        SystemOperateLog logDO = SystemOperateLogConvert.INSTANCE.convert(createReqDTO);
        operateLogMapper.insert(logDO);
    }

    @Override
    public PageResult<SystemOperateLogDTO> getOperateLogPage(QueryOperateLogPageReqVO reqVO) {
        Page<SystemOperateLog> pageResult = this.operateLogMapper.selectOperateLogPage(reqVO);

        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return PageResult.empty();
        }
        List<SystemOperateLog> records = pageResult.getRecords();
        List<SystemOperateLogDTO> operateLogDTOS = SystemLoggerConvert.INSTANCE.entityToOperateLogDTOS(records);
        // to nickName
        List<Long> userIds = operateLogDTOS.stream().map(SystemOperateLogDTO::getUserId).collect(Collectors.toList());
        List<SystemUser> systemUsers = systemUserMapper.selectBatchIds(userIds);
        Map<Long, String> map = systemUsers.stream().collect(Collectors.toMap(SystemUser::getId, SystemUser::getNickname));
        operateLogDTOS.forEach(operateLog -> {
            Long userId = operateLog.getUserId();
            if (map.containsKey(userId)) {
                operateLog.setUserNickname(map.get(userId));
            }
        });
        return new PageResult<>(operateLogDTOS, pageResult.getTotal());
    }
}
