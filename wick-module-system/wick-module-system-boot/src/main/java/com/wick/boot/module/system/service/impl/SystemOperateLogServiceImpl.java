package com.wick.boot.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.convert.SystemLoggerConvert;
import com.wick.boot.module.system.convert.SystemOperateLogConvert;
import com.wick.boot.module.system.enums.system.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.SystemOperateLogMapper;
import com.wick.boot.module.system.mapper.SystemUserMapper;
import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;
import com.wick.boot.module.system.model.dto.logger.operate.SystemOperateLogDTO;
import com.wick.boot.module.system.model.entity.SystemOperateLog;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.vo.logger.operate.SystemOperateLogExportVO;
import com.wick.boot.module.system.model.vo.logger.operate.SystemOperateLogQueryVO;
import com.wick.boot.module.system.service.SystemOperateLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 操作日志-服务实现类
 *
 * @author Wickson
 * @date 2024-06-04
 */
@Service
public class SystemOperateLogServiceImpl implements SystemOperateLogService {

    @Resource
    private SystemOperateLogMapper operateLogMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        SystemOperateLog logDO = SystemOperateLogConvert.INSTANCE.convert(createReqDTO);
        operateLogMapper.insert(logDO);
    }

    @Override
    public PageResult<SystemOperateLogDTO> getOperateLogPage(SystemOperateLogQueryVO reqVO) {
        Page<SystemOperateLog> pageResult = this.operateLogMapper.selectOperateLogPage(reqVO);

        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return PageResult.empty();
        }
        List<SystemOperateLog> records = pageResult.getRecords();
        List<SystemOperateLogDTO> operateLogDTOS = SystemLoggerConvert.INSTANCE.entityToOperateLogDTOS(records);
        // to userName
        List<Long> userIds = operateLogDTOS.stream().map(SystemOperateLogDTO::getUserId).collect(Collectors.toList());
        List<SystemUser> systemUsers = systemUserMapper.selectBatchIds(userIds);
        Map<Long, String> map = systemUsers.stream().collect(Collectors.toMap(SystemUser::getId, SystemUser::getNickname));
        operateLogDTOS.forEach(operateLog -> {
            Long userId = operateLog.getUserId();
            if (map.containsKey(userId)) {
                operateLog.setUserName(map.get(userId));
            }
        });
        return new PageResult<>(operateLogDTOS, pageResult.getTotal());
    }

    @Override
    public void exportOperateLog(SystemOperateLogQueryVO queryParams, HttpServletResponse response) {
        String fileName = "用户操作日志.xlsx";
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            List<SystemOperateLog> exportOperateLog = this.operateLogMapper.selectOperateLogPage(queryParams).getRecords();
            EasyExcel.write(response.getOutputStream(), SystemOperateLogExportVO.class)
                    .sheet("用户操作日志")
                    .doWrite(BeanUtil.copyToList(exportOperateLog, SystemOperateLogExportVO.class));
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.OPERATE_LOG_EXPORT_ERROR);
        }
    }
}
