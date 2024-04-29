package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemRoleService;
import com.wick.boot.module.system.model.dto.SystemRoleDTO;
import com.wick.boot.module.system.model.vo.role.AddRoleVo;
import com.wick.boot.module.system.model.vo.role.QueryRolePageReqVO;
import com.wick.boot.module.system.model.vo.role.UpdateRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    private ISystemRoleService systemRoleService;

    @PostMapping
    @ApiOperation(value = "新增角色信息", notes = "角色信息")
    public ResultUtil<Long> addRole(@Valid @RequestBody AddRoleVo reqVO) {
        this.systemRoleService.addRole(reqVO);
        return ResultUtil.success();
    }

    @PutMapping
    @ApiOperation(value = "编辑角色信息", notes = "角色信息")
    public ResultUtil<Boolean> updateRole(@Valid @RequestBody UpdateRoleVo reqVO) {
        this.systemRoleService.updateRole(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除角色信息", notes = "角色信息")
    @ApiImplicitParam(name = "ids", value = "角色数据ID", required = true)
    public ResultUtil<Long> deleteRole(@PathVariable("ids") List<Long> ids) {
        this.systemRoleService.deleteRole(ids);
        return ResultUtil.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取角色分页", notes = "角色信息")
    public ResultUtil<PageResult<SystemRoleDTO>> getUserPage(@Valid QueryRolePageReqVO reqVO) {
        return ResultUtil.success(systemRoleService.getRolePage(reqVO));
    }

}
