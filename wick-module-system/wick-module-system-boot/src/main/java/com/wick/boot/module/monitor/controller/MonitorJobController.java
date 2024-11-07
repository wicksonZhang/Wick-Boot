package com.wick.boot.module.monitor.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.monitor.model.dto.job.MonitorJobDTO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobAddVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobQueryVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobUpdateVO;
import com.wick.boot.module.monitor.service.MonitorJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 定时任务调度管理-控制类
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
@RestController
@RequestMapping("/monitor/job")
@Api(tags = "03-系统监控-定时任务调度")
public class MonitorJobController {

    @Resource
    private MonitorJobService monitorJobService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('monitor:job:add')")
    @ApiOperation(value = "新增_定时任务调度接口", notes = "定时任务调度管理")
    public ResultUtil<Long> add(@Valid @RequestBody MonitorJobAddVO reqVO) {
        this.monitorJobService.addMonitorJob(reqVO);
        return ResultUtil.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('monitor:job:update')")
    @ApiOperation(value = "修改_定时任务调度接口", notes = "定时任务调度管理")
    public ResultUtil<Boolean> update(@Valid @RequestBody MonitorJobUpdateVO reqVO) {
        this.monitorJobService.updateMonitorJob(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('monitor:job:delete')")
    @ApiOperation(value = "删除_定时任务调度接口", notes = "定时任务调度管理")
    @ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> remove(@PathVariable("ids") List<Long> ids) {
        this.monitorJobService.deleteMonitorJob(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('monitor:job:query')")
    @ApiOperation(value = "按ID查询_定时任务调度接口", notes = "定时任务调度管理")
    @ApiImplicitParam(name = "id", value = "定时任务调度ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<MonitorJobDTO> getMonitorJob(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return ResultUtil.success(monitorJobService.getMonitorJob(id));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('monitor:job:query')")
    @ApiOperation(value = "分页查询_定时任务调度接口", notes = "定时任务调度管理")
    public ResultUtil<PageResult<MonitorJobDTO>> getMonitorJobPage(@Valid MonitorJobQueryVO reqVO) {
        return ResultUtil.success(monitorJobService.getMonitorJobPage(reqVO));
    }

}