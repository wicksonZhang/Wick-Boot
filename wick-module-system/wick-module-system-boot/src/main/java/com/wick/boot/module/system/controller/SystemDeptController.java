package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemDeptService;
import com.wick.boot.module.system.model.dto.dept.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.dept.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.vo.dept.AddDeptReqVO;
import com.wick.boot.module.system.model.vo.dept.QueryDeptListReqVO;
import com.wick.boot.module.system.model.vo.dept.UpdateDeptReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @ApiOperation(value = "编辑部门数据", notes = "部门信息")
    @PutMapping
    @PreAuthorize("@ss.hasPerm('sys:dept:edit')")
    public ResultUtil<Boolean> editDepartment(@Valid @RequestBody UpdateDeptReqVO reqVO) {
        systemDeptService.updateDepartment(reqVO);
        return ResultUtil.success();
    }

    @ApiOperation(value = "删除部门数据", notes = "部门信息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dept:delete')")
    @ApiImplicitParam(name = "ids", value = "部门ID", required = true)
    public ResultUtil<Long> deleteDept(@NotEmpty(message = "部门信息主键不能为空")
                                       @PathVariable("ids") List<Long> ids) {
        systemDeptService.deleteDept(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过部门ID获取部门数据", notes = "部门信息")
    @ApiImplicitParam(name = "id", value = "部门ID", required = true)
    public ResultUtil<SystemDeptDTO> getDepartment(@NotNull(message = "部门ID不能为空")
                                                   @PathVariable("id") Long id) {
        return ResultUtil.success(systemDeptService.getDepartmentById(id));
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
