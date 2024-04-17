package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemDeptService;
import com.wick.boot.module.system.model.dto.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.vo.dept.AddDeptReqVO;
import com.wick.boot.module.system.model.vo.dept.QueryDeptListReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理 - 部门信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-03
 */
@RestController
@RequestMapping("/dept")
@Api(tags = "系统管理 - 部门信息")
public class SystemDeptController {

    @Resource
    private ISystemDeptService systemDeptService;

    @ApiOperation(value = "新增部门数据", notes = "部门信息")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dept:add')")
    public ResultUtil<Long> addDepartment(@Valid @RequestBody AddDeptReqVO reqVO) {
        systemDeptService.addDepartment(reqVO);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取部门列表", notes = "部门信息")
    @GetMapping
    public ResultUtil<List<SystemDeptDTO>> listDepartments(@Valid QueryDeptListReqVO reqVO) {
        return ResultUtil.success(systemDeptService.listDepartments(reqVO));
    }

    @ApiOperation(value = "获取部门下拉选项", notes = "部门信息")
    @GetMapping("/options")
    public ResultUtil<List<SystemDeptOptionsDTO>> listDeptOptions() {
        return ResultUtil.success(systemDeptService.listDeptOptions());
    }
}
