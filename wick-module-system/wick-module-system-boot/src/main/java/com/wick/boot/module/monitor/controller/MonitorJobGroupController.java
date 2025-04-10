package com.wick.boot.module.monitor.controller;

import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.monitor.model.dto.jobgroup.MonitorJobGroupDTO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupAddVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupQueryVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupUpdateVO;
import com.wick.boot.module.monitor.service.MonitorJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 定时任务调度管理-控制类
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
@RestController
@RequestMapping("/monitor/job-group")
@Api(tags = "03-定时任务-执行器管理")
public class MonitorJobGroupController {

    @Resource
    private MonitorJobService monitorJobService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('monitor:job-group:add')")
    @ApiOperation(value = "新增_执行器管理接口", notes = "执行器管理管理")
    public ResultUtil<Long> add(@Valid @RequestBody MonitorJobGroupAddVO reqVO) {
        this.monitorJobService.addMonitorJobGroup(reqVO);
        return ResultUtil.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('monitor:job-group:update')")
    @ApiOperation(value = "修改_执行器管理接口", notes = "执行器管理管理")
    public ResultUtil<Boolean> update(@Valid @RequestBody MonitorJobGroupUpdateVO reqVO) {
        this.monitorJobService.updateMonitorJobGroup(reqVO);
        return ResultUtil.success();
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('monitor:job-group:delete')")
    @ApiOperation(value = "删除_执行器管理接口", notes = "执行器管理管理")
    @ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> remove(@NotEmpty(message = "执行器编号不能为空") @PathVariable("ids") List<Long> ids) {
        this.monitorJobService.deleteMonitorJobGroup(ids);
        return ResultUtil.success();
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('monitor:job-group:query')")
    @ApiOperation(value = "分页查询_执行器管理接口", notes = "执行器管理管理")
    public ResultUtil<PageResult<MonitorJobGroupDTO>> getMonitorJobPage(@Valid MonitorJobGroupQueryVO reqVO) {
        return ResultUtil.success(monitorJobService.getMonitorJobPage(reqVO));
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('monitor:job-group:query')")
    @ApiOperation(value = "集合查询_执行器管理接口", notes = "执行器管理管理")
    public ResultUtil<List<OptionDTO<Integer>>> getMonitorJobList() {
        return ResultUtil.success(monitorJobService.getMonitorJobList());
    }

}