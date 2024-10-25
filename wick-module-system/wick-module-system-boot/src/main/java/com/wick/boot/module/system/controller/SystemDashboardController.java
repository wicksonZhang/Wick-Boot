package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.dto.dashboard.SystemDashboardVisitStatsDTO;
import com.wick.boot.module.system.model.vo.dashboard.SystemDashboardQueryVO;
import com.wick.boot.module.system.service.SystemLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 首页管理 - 控制类
 *
 * @author Wickson
 * @date 2024-10-16
 */
@RestController
@RequestMapping("/system/dashboard")
@Api(value = "/system/dashboard", tags = {"00-首页管理"})
public class SystemDashboardController {

    @Resource
    private SystemLoginLogService loginLogService;

    @GetMapping("/visit-stats")
    @ApiOperation(value = "获取_统计数据", notes = "首页管理", httpMethod = "GET")
    public ResultUtil<List<SystemDashboardVisitStatsDTO>> getVisitStats() {
        return ResultUtil.success(loginLogService.getVisitStats());
    }

    @GetMapping("/visit-trend")
    @ApiOperation(value = "获取_访问趋势", notes = "首页管理", httpMethod = "GET")
    public ResultUtil<Map<String, Object>> getVisitTrend(@Validated SystemDashboardQueryVO queryVO) {
        // 参数验证
        queryVO.validateParam();

        return ResultUtil.success(loginLogService.getVisitTrend(queryVO));
    }


}
