package com.wick.boot.module.monitor.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobAddVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobUpdateVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobQueryVO;
import com.wick.boot.module.monitor.model.dto.job.MonitorJobDTO;

import java.util.List;

/**
 * 定时任务调度-应用服务类
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
public interface MonitorJobService {

    /**
     * 新增定时任务调度数据
     *
     * @param reqVO 新增请求参数
     */
    Long addMonitorJob(MonitorJobAddVO reqVO);

    /**
     * 更新定时任务调度数据
     *
     * @param reqVO 更新请求参数
     */
    void updateMonitorJob(MonitorJobUpdateVO reqVO);

    /**
     * 删除新增定时任务调度数据
     *
     * @param ids 主键集合
     */
    void deleteMonitorJob(List<Long> ids);

    /**
     * 通过主键获取定时任务调度数据
     *
     * @param id 定时任务调度ID
     * @return MonitorJobDTO 定时任务调度DTO
     */
     MonitorJobDTO getMonitorJob(Long id);

    /**
     * 获取定时任务调度分页数据
     *
     * @param queryParams 分页查询参数
     * @return MonitorJobDTO 定时任务调度DTO
     */
    PageResult<MonitorJobDTO> getMonitorJobPage(MonitorJobQueryVO queryParams);
}