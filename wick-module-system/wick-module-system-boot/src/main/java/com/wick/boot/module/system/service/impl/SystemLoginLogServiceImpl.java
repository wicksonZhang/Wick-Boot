package com.wick.boot.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.convert.SystemDashboardConvert;
import com.wick.boot.module.system.convert.SystemLoggerConvert;
import com.wick.boot.module.system.enums.system.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.SystemLoginLogMapper;
import com.wick.boot.module.system.mapper.SystemUserMapper;
import com.wick.boot.module.system.model.dto.LoginLogReqDTO;
import com.wick.boot.module.system.model.dto.SystemDashboardVisitStatsDTO;
import com.wick.boot.module.system.model.dto.dashboard.SystemDashboardVisitDTO;
import com.wick.boot.module.system.model.dto.dashboard.SystemDashboardVisitTrendDTO;
import com.wick.boot.module.system.model.dto.logger.login.SystemLoginLogDTO;
import com.wick.boot.module.system.model.entity.SystemLoginLog;
import com.wick.boot.module.system.model.vo.dashboard.SystemDashboardQueryVO;
import com.wick.boot.module.system.model.vo.logger.login.SystemLoginLogExportVO;
import com.wick.boot.module.system.model.vo.logger.login.SystemLoginLogQueryVO;
import com.wick.boot.module.system.service.SystemLoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 登录日志-服务实现层
 *
 * @author Wickson
 * @date 2024-06-04
 */
@Service
public class SystemLoginLogServiceImpl implements SystemLoginLogService {

    @Resource
    private SystemLoginLogMapper loginLogMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private RedisService redisService;

    @Override
    public void createLoginLog(LoginLogReqDTO logReqDTO) {
        SystemLoginLog systemLoginLog = new SystemLoginLog();
        BeanUtil.copyProperties(logReqDTO, systemLoginLog);
        this.loginLogMapper.insert(systemLoginLog);
    }

    @Override
    public PageResult<SystemLoginLogDTO> getLoginLogPage(SystemLoginLogQueryVO reqVO) {
        Page<SystemLoginLog> pageResult = this.loginLogMapper.selectLoginLogPage(reqVO);

        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return PageResult.empty();
        }

        List<SystemLoginLogDTO> loginLogDTOS = SystemLoggerConvert.INSTANCE.entityToLoginLogDTOS(pageResult.getRecords());
        return new PageResult<>(loginLogDTOS, pageResult.getTotal());
    }

    @Override
    public void exportLoginLog(SystemLoginLogQueryVO queryParams, HttpServletResponse response) {
        String fileName = "用户登录日志.xlsx";
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            List<SystemLoginLog> exportLoginLog = this.loginLogMapper.selectLoginLogPage(queryParams).getRecords();
            EasyExcel.write(response.getOutputStream(), SystemLoginLogExportVO.class)
                    .sheet("用户登录日志")
                    .doWrite(BeanUtil.copyToList(exportLoginLog, SystemLoginLogExportVO.class));
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.LOGIN_LOG_EXPORT_ERROR);
        }
    }

    @Override
    public SystemDashboardVisitStatsDTO getVisitStats() {
        SystemDashboardVisitDTO systemDashboardVisitDTO = this.loginLogMapper.selectVisitStats();
        return SystemDashboardConvert.INSTANCE.convertToDTO(systemDashboardVisitDTO);
    }

    @Override
    public Map<String, Object> getVisitTrend(SystemDashboardQueryVO queryVO) {
        // 获取查询参数的起止时间
        LocalDate startDate = queryVO.getStartDate();
        LocalDate endDate = queryVO.getEndDate();

        // 生成日期范围的初始数据
        Map<String, Integer> dateMap = generateDateMap(startDate, endDate);

        // 从数据库获取趋势统计数据
        Map<String, SystemDashboardVisitTrendDTO> trendDataMap = this.loginLogMapper.selectVisitTrend(startDate, endDate);

        // 合并日期数据和趋势数据，生成最终结果
        return mergeTrendResults(dateMap, trendDataMap);
    }

    private Map<String, Integer> generateDateMap(LocalDate startDate, LocalDate endDate) {
        // 创建有序的日期Map，将每一天初始化为0
        Map<String, Integer> dateMap = Maps.newLinkedHashMap();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dateMap.put(date.toString(), 0);  // 格式化日期为字符串
        }
        return dateMap;
    }

    private Map<String, Object> mergeTrendResults(Map<String, Integer> dateMap, Map<String, SystemDashboardVisitTrendDTO> trendDataMap) {
        // 定义日期、PV、UV、登录成功的统计结果列表
        List<String> dates = Lists.newArrayList();
        List<Integer> pvList = Lists.newArrayList();
        List<Integer> uvList = Lists.newArrayList();

        // 遍历日期区间，并将对应的趋势数据填入列表
        dateMap.forEach((date, defaultValue) -> {
            dates.add(date);
            SystemDashboardVisitTrendDTO trendDTO = trendDataMap.get(date);
            if (trendDTO != null) {
                pvList.add(trendDTO.getPv());
                uvList.add(trendDTO.getUv());
            } else {
                // 如果没有数据，使用默认值0
                pvList.add(defaultValue);
                uvList.add(defaultValue);
            }
        });

        // 将结果封装到Map中
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("dates", dates);
        resultMap.put("pvList", pvList);
        resultMap.put("uvList", uvList);

        return resultMap;
    }

}
