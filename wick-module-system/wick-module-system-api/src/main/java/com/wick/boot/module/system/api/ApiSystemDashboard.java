package com.wick.boot.module.system.api;

import com.wick.boot.module.system.model.dto.SystemDashboardVisitStatsDTO;

import java.util.List;

/**
 * @author Wickson
 * @date 2024-10-30
 */
public interface ApiSystemDashboard {

    /**
     * 获取_统计数据
     *
     * @return
     */
    List<SystemDashboardVisitStatsDTO> getVisitStatsList();

}
