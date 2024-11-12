package com.wick.boot.module.monitor.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.xxl.job.model.vo.jobgroup.XxlJobGroupVO;
import com.wick.boot.module.monitor.model.dto.jobgroup.MonitorJobGroupDTO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupAddVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupQueryVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupUpdateVO;

import java.util.List;

/**
 * 执行器管理-应用服务类
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
public interface MonitorJobService {

    /**
     * 新增执行器管理
     *
     * @param reqVO 新增请求参数
     */
    void addMonitorJobGroup(MonitorJobGroupAddVO reqVO);

    /**
     * 更新执行器管理
     *
     * @param reqVO 更新请求参数
     */
    void updateMonitorJobGroup(MonitorJobGroupUpdateVO reqVO);

    /**
     * 删除新增执行器管理
     *
     * @param ids 主键集合
     */
    void deleteMonitorJobGroup(List<Long> ids);

    /**
     * 通过主键获取执行器管理
     *
     * @param id 定时任务调度ID
     * @return MonitorJobGroupDTO 定时任务调度DTO
     */
     MonitorJobGroupDTO getMonitorJob(Long id);

    /**
     * 获取执行器管理分页数据
     *
     * @param queryParams 分页查询参数
     * @return MonitorJobGroupDTO 执行器管理DTO
     */
    PageResult<XxlJobGroupVO> getMonitorJobPage(MonitorJobGroupQueryVO queryParams);
}