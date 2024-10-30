package com.wick.boot.module.system.api.service;

import com.wick.boot.module.system.api.ApiSystemDashboard;
import com.wick.boot.module.system.model.dto.SystemDashboardVisitStatsDTO;
import com.wick.boot.module.system.service.SystemLoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统首页 Api
 *
 * @author Wickson
 * @date 2024-10-30
 */
@Service
public class ApiSystemDashboardService implements ApiSystemDashboard {

    @Resource
    private SystemLoginLogService loginLogService;

    @Override
    public List<SystemDashboardVisitStatsDTO> getVisitStatsList() {
        return loginLogService.getVisitStats();
    }

}
