package com.wick.boot.module.monitor.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoAddVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoUpdateVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoQueryVO;
import com.wick.boot.module.monitor.model.dto.jobinfo.MonitorXxlJobInfoDTO;

import java.util.List;

/**
 * 定时任务管理-应用服务类
 *
 * @author Wickson
 * @date 2024-11-13 10:24
 */
public interface MonitorXxlJobInfoService {

    /**
     * 新增定时任务管理数据
     *
     * @param reqVO 新增请求参数
     */
    void addMonitorXxlJobInfo(MonitorXxlJobInfoAddVO reqVO);

    /**
     * 更新定时任务管理数据
     *
     * @param reqVO 更新请求参数
     */
    void updateMonitorXxlJobInfo(MonitorXxlJobInfoUpdateVO reqVO);

    /**
     * 删除新增定时任务管理数据
     *
     * @param ids 主键集合
     */
    void deleteMonitorXxlJobInfo(List<Long> ids);

    /**
     * 获取定时任务管理分页数据
     *
     * @param queryParams 分页查询参数
     * @return MonitorXxlJobInfoDTO 定时任务管理DTO
     */
    PageResult<MonitorXxlJobInfoDTO> getMonitorXxlJobInfoPage(MonitorXxlJobInfoQueryVO queryParams);
}