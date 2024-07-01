package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.app.service.ISystemLoginLogService;
import com.wick.boot.module.system.convert.SystemDictTypeConvert;
import com.wick.boot.module.system.convert.SystemLoggerConvert;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.ISystemLoginLogMapper;
import com.wick.boot.module.system.model.dto.LoginLogReqDTO;
import com.wick.boot.module.system.model.dto.SystemDictTypeDTO;
import com.wick.boot.module.system.model.dto.SystemLoginLogDTO;
import com.wick.boot.module.system.model.dto.SystemUserDTO;
import com.wick.boot.module.system.model.entity.SystemLoginLog;
import com.wick.boot.module.system.model.vo.logger.login.LoginLogExportVO;
import com.wick.boot.module.system.model.vo.logger.login.QueryLoginLogPageReqVO;
import com.wick.boot.module.system.model.vo.user.UserExportVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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

    @Override
    public PageResult<SystemLoginLogDTO> getLoginLogPage(QueryLoginLogPageReqVO reqVO) {
        Page<SystemLoginLog> pageResult = this.loginLogMapper.selectLoginLogPage(reqVO);

        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return PageResult.empty();
        }

        List<SystemLoginLogDTO> loginLogDTOS = SystemLoggerConvert.INSTANCE.entityToLoginLogDTOS(pageResult.getRecords());
        return new PageResult<>(loginLogDTOS, pageResult.getTotal());
    }

    @Override
    public void exportLoginLog(QueryLoginLogPageReqVO queryParams, HttpServletResponse response) {
        String fileName = "用户登录日志.xlsx";
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            List<SystemLoginLog> exportLoginLog = this.loginLogMapper.selectLoginLogPage(queryParams).getRecords();
            EasyExcel.write(response.getOutputStream(), LoginLogExportVO.class)
                    .sheet("用户登录日志")
                    .doWrite(BeanUtil.copyToList(exportLoginLog, LoginLogExportVO.class));
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.LOGIN_LOG_EXPORT_ERROR);
        }
    }
}
