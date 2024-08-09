package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.service.SystemRoleService;
import com.wick.boot.module.system.model.dto.role.SystemRoleDTO;
import com.wick.boot.module.system.model.dto.role.SystemRoleOptionsDTO;
import com.wick.boot.module.system.model.vo.role.AddRoleVo;
import com.wick.boot.module.system.model.vo.role.QueryRolePageReqVO;
import com.wick.boot.module.system.model.vo.role.UpdateRoleVo;
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
 * 系统管理 - 角色信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@RestController
@RequestMapping("/roles")
@Api(tags = "系统管理 - 角色信息")
public class SystemRoleController {

    @Resource
    private SystemRoleService systemRoleService;

    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:role:add')")
    @ApiOperation(value = "新增角色信息", notes = "角色信息")
    public ResultUtil<Long> addRole(@Valid @RequestBody AddRoleVo reqVO) {
        this.systemRoleService.addRole(reqVO);
        return ResultUtil.success();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPerm('sys:role:edit')")
    @ApiOperation(value = "编辑角色信息", notes = "角色信息")
    public ResultUtil<Boolean> updateRole(@Valid @RequestBody UpdateRoleVo reqVO) {
        this.systemRoleService.updateRole(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:role:delete')")
    @ApiOperation(value = "删除角色信息", notes = "角色信息")
    @ApiImplicitParam(name = "ids", value = "角色数据ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> deleteRole(@PathVariable("ids") List<Long> ids) {
        this.systemRoleService.deleteRole(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取角色信息ById", notes = "角色信息")
    @ApiImplicitParam(name = "id", value = "角色信息ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemRoleDTO> getRoleById(@NotNull(message = "角色信息主键不能为空")
                                                 @PathVariable("id") Long id) {
        return ResultUtil.success(systemRoleService.getRoleById(id));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取角色分页", notes = "角色信息")
    public ResultUtil<PageResult<SystemRoleDTO>> getUserPage(@Valid QueryRolePageReqVO reqVO) {
        return ResultUtil.success(systemRoleService.getRolePage(reqVO));
    }

    @ApiOperation(value = "获取角色下拉选项", notes = "角色信息")
    @GetMapping("/options")
    public ResultUtil<List<SystemRoleOptionsDTO>> listRoleOptions() {
        return ResultUtil.success(systemRoleService.listRoleOptions());
    }

    @GetMapping("/{roleId}/menuIds")
    @ApiOperation(value = "获取角色的菜单ID集合", notes = "角色信息")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<List<Long>> getRoleMenuIds(@PathVariable("roleId") Long roleId) {
        return ResultUtil.success(systemRoleService.getRoleMenuIds(roleId));
    }

    @PutMapping("/{roleId}/menus")
    @PreAuthorize("@ss.hasPerm('sys:role:edit')")
    @ApiOperation(value = "分配菜单(包括按钮权限)给角色", notes = "角色信息")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> assignMenusToRole(@PathVariable("roleId") Long roleId,
                                              @NotEmpty(message = "菜单集合不能为空")
                                              @RequestBody List<Long> menuIds) {
        systemRoleService.assignMenusToRole(roleId, menuIds);
        return ResultUtil.success();
    }

}
