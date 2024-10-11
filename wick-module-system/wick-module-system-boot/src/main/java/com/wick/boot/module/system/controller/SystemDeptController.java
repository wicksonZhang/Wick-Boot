package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.vo.dept.SystemDeptAddVO;
import com.wick.boot.module.system.service.SystemDeptService;
import com.wick.boot.module.system.model.dto.dept.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.dept.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.vo.dept.SystemDeptQueryVO;
import com.wick.boot.module.system.model.vo.dept.SystemDeptUpdateVO;
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
 * @author Wickson
 * @date 2024-04-03
 */
@RestController
@RequestMapping("/system/dept")
@Api(tags = "部门信息")
public class SystemDeptController {

    @Resource
    private SystemDeptService systemDeptService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('system:dept:add')")
    @ApiOperation(value = "新增部门数据", notes = "部门信息")
    public ResultUtil<Long> add(@Valid @RequestBody SystemDeptAddVO reqVO) {
        return ResultUtil.success(systemDeptService.addSystemDept(reqVO));
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('system:dept:update')")
    @ApiOperation(value = "编辑部门数据", notes = "部门信息")
    public ResultUtil<Boolean> update(@Valid @RequestBody SystemDeptUpdateVO reqVO) {
        systemDeptService.updateSystemDept(reqVO);
        return ResultUtil.success();
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('system:dept:delete')")
    @ApiOperation(value = "删除部门数据", notes = "部门信息")
    @ApiImplicitParam(name = "ids", value = "部门ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> remove(@NotEmpty(message = "部门信息主键不能为空") @PathVariable List<Long> ids) {
        systemDeptService.deleteSystemDept(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('system:dept:query')")
    @ApiOperation(value = "获取部门数据", notes = "部门信息")
    @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemDeptDTO> getSystemDept(@NotNull(message = "部门ID不能为空") @PathVariable Long id) {
        return ResultUtil.success(systemDeptService.getSystemDept(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('system:dept:query')")
    @ApiOperation(value = "获取部门列表", notes = "部门信息")
    public ResultUtil<List<SystemDeptDTO>> getSystemDeptList(@Valid SystemDeptQueryVO reqVO) {
        return ResultUtil.success(systemDeptService.getSystemDeptList(reqVO));
    }

    @GetMapping("/options")
    @PreAuthorize("@ss.hasPerm('system:dept:options')")
    @ApiOperation(value = "获取部门下拉选项", notes = "部门信息")
    public ResultUtil<List<SystemDeptOptionsDTO>> getSystemDeptOptionsList() {
        return ResultUtil.success(systemDeptService.getSystemDeptOptionsList());
    }
}
