package com.wick.boot.module.monitor.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.monitor.model.dto.jobinfo.MonitorXxlJobInfoDTO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoAddVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoQueryVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoTriggerVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoUpdateVO;
import com.wick.boot.module.monitor.service.MonitorXxlJobInfoService;
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
 * 定时任务管理管理-控制类
 *
 * @author Wickson
 * @date 2024-11-13 10:00
 */
@RestController
@RequestMapping("/monitor/job-info")
@Api(tags = "03-定时任务-定时任务管理")
public class MonitorXxlJobInfoController {

    @Resource
    private MonitorXxlJobInfoService monitorXxlJobInfoService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('monitor:job-info:add')")
    @ApiOperation(value = "新增_定时任务管理接口", notes = "定时任务管理管理")
    public ResultUtil<Boolean> add(@Valid @RequestBody MonitorXxlJobInfoAddVO reqVO) {
        this.monitorXxlJobInfoService.addMonitorXxlJobInfo(reqVO);
        return ResultUtil.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('monitor:job-info:update')")
    @ApiOperation(value = "修改_定时任务管理接口", notes = "定时任务管理管理")
    public ResultUtil<Boolean> update(@Valid @RequestBody MonitorXxlJobInfoUpdateVO reqVO) {
        this.monitorXxlJobInfoService.updateMonitorXxlJobInfo(reqVO);
        return ResultUtil.success();
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('monitor:job-info:delete')")
    @ApiOperation(value = "删除_定时任务管理接口", notes = "定时任务管理管理")
    @ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> remove(@PathVariable("ids") List<Long> ids) {
        this.monitorXxlJobInfoService.deleteMonitorXxlJobInfo(ids);
        return ResultUtil.success();
    }

    @ApiOperation(value = "启用(停用)_定时任务管理接口", notes = "定时任务管理管理")
    @PatchMapping("/updateStatus/{id}/{status}")
    public ResultUtil<Long> updateStatus(@NotNull(message = "定时任务编号不能为空") @PathVariable Integer id,
                                         @NotNull(message = "调度状态不能为空") @PathVariable Integer status) {
        this.monitorXxlJobInfoService.updateStatus(id, status);
        return ResultUtil.success();
    }

    @ApiOperation(value = "执行_定时任务管理接口", notes = "定时任务管理管理")
    @PostMapping("/executeTrigger")
    public ResultUtil<Long> executeTrigger(@Valid @RequestBody MonitorXxlJobInfoTriggerVO addVO) {
        this.monitorXxlJobInfoService.executeTrigger(addVO);
        return ResultUtil.success();
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('monitor:job-info:query')")
    @ApiOperation(value = "分页查询_定时任务管理接口", notes = "定时任务管理管理")
    public ResultUtil<PageResult<MonitorXxlJobInfoDTO>> getMonitorXxlJobInfoPage(@Valid MonitorXxlJobInfoQueryVO reqVO) {
        return ResultUtil.success(monitorXxlJobInfoService.getMonitorXxlJobInfoPage(reqVO));
    }

}