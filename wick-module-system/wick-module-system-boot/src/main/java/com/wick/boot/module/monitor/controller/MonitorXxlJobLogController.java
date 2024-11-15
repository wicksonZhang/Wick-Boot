package com.wick.boot.module.monitor.controller;

import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.monitor.model.dto.joblog.MonitorXxlJobLogDTO;
import com.wick.boot.module.monitor.model.vo.joblog.MonitorXxlJobLogQueryVO;
import com.wick.boot.module.monitor.service.MonitorXxlJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 调度日志管理-控制类
 *
 * @author Wickson
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/monitor/job-log")
@Api(tags = "01-定时任务-调度日志")
public class MonitorXxlJobLogController {

    @Resource
    private MonitorXxlJobLogService xxlJobLogService;

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('monitor:job-log:query')")
    @ApiOperation(value = "分页查询_调度日志接口", notes = "调度日志管理")
    public ResultUtil<PageResult<MonitorXxlJobLogDTO>> getMonitorXxlJobLogPage(@Valid MonitorXxlJobLogQueryVO reqVO) {
        return ResultUtil.success(xxlJobLogService.getMonitorXxlJobLogPage(reqVO));
    }

    @GetMapping("/getJobsByGroup/{jobGroup}")
    @PreAuthorize("@ss.hasPerm('monitor:job-log:query')")
    @ApiOperation(value = "分页查询_调度日志接口", notes = "调度日志管理")
    public ResultUtil<List<OptionDTO<Integer>>> getJobsByGroup(@NotNull(message = "执行器不能为空") @PathVariable Long jobGroup) {
        return ResultUtil.success(xxlJobLogService.getJobsByGroup(jobGroup));
    }

}
