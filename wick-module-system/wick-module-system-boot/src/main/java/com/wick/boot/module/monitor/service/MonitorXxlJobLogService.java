package com.wick.boot.module.monitor.service;

import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.monitor.model.dto.joblog.MonitorXxlJobLogDTO;
import com.wick.boot.module.monitor.model.vo.joblog.MonitorXxlJobLogQueryVO;

import java.util.List;

/**
 * 调度日志-应用服务类
 *
 * @author Wickson
 * @date 2024-11-15 14:22
 */
public interface MonitorXxlJobLogService {

    /**
     * 获取调度日志分页数据
     *
     * @param queryParams 分页查询参数
     * @return MonitorXxlJobLogDTO 调度日志DTO
     */
    PageResult<MonitorXxlJobLogDTO> getMonitorXxlJobLogPage(MonitorXxlJobLogQueryVO queryParams);

    /**
     * 获取定时任务列表
     *
     * @param jobGroup 执行器
     * @return option集合
     */
    List<OptionDTO<Integer>> getJobsByGroup(Long jobGroup);

}