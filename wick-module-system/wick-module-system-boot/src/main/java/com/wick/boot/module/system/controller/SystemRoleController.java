package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.dto.role.SystemRoleDTO;
import com.wick.boot.module.system.model.dto.role.SystemRoleOptionsDTO;
import com.wick.boot.module.system.model.vo.role.SystemRoleAddVO;
import com.wick.boot.module.system.model.vo.role.SystemRoleQueryVO;
import com.wick.boot.module.system.model.vo.role.SystemRoleUpdateVO;
import com.wick.boot.module.system.service.SystemRoleService;
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
 * 角色信息-控制类
 *
 * @author Wickson
 * @date 2024-04-02
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色信息管理接口")
public class SystemRoleController {

    @Resource
    private SystemRoleService systemRoleService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('system:role:add')")
    @ApiOperation(value = "新增角色信息", notes = "角色管理")
    public ResultUtil<Long> add(@Valid @RequestBody SystemRoleAddVO reqVO) {
        return ResultUtil.success(this.systemRoleService.addSystemRole(reqVO));
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('system:role:update')")
    @ApiOperation(value = "编辑角色信息", notes = "角色管理")
    public ResultUtil<Boolean> update(@Valid @RequestBody SystemRoleUpdateVO reqVO) {
        this.systemRoleService.updateSystemRole(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('system:role:delete')")
    @ApiOperation(value = "删除角色信息", notes = "角色管理")
    @ApiImplicitParam(name = "ids", value = "角色数据ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> delete(@PathVariable("ids") List<Long> ids) {
        this.systemRoleService.deleteSystemRole(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取角色信息ById", notes = "角色管理")
    @ApiImplicitParam(name = "id", value = "角色信息ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemRoleDTO> getSystemRole(@NotNull(message = "角色信息主键不能为空") @PathVariable Long id) {
        return ResultUtil.success(systemRoleService.getSystemRole(id));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取角色分页", notes = "角色管理")
    public ResultUtil<PageResult<SystemRoleDTO>> getSystemRolePage(@Valid SystemRoleQueryVO reqVO) {
        return ResultUtil.success(systemRoleService.getSystemRolePage(reqVO));
    }

    @ApiOperation(value = "获取角色下拉选项", notes = "角色管理")
    @GetMapping("/options")
    public ResultUtil<List<SystemRoleOptionsDTO>> listRoleOptions() {
        return ResultUtil.success(systemRoleService.listRoleOptions());
    }

    @GetMapping("/{id}/menuIds")
    @ApiOperation(value = "获取角色的菜单ID集合", notes = "角色管理")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<List<Long>> getRoleMenuIds(@PathVariable Long id) {
        return ResultUtil.success(systemRoleService.getRoleMenuIds(id));
    }

    @PutMapping("/{roleId}/menus")
    @PreAuthorize("@ss.hasPerm('system:role:assign')")
    @ApiOperation(value = "分配菜单(包括按钮权限)给角色", notes = "角色管理")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> assignMenusToRole(@PathVariable("roleId") Long roleId,
                                              @NotEmpty(message = "菜单集合不能为空")
                                              @RequestBody List<Long> menuIds) {
        systemRoleService.assignMenusToRole(roleId, menuIds);
        return ResultUtil.success();
    }

}
