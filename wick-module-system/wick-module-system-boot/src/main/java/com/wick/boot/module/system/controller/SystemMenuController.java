package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemMenuService;
import com.wick.boot.module.system.model.dto.menu.SystemMenuDTO;
import com.wick.boot.module.system.model.dto.menu.SystemMenuOptionsDTO;
import com.wick.boot.module.system.model.dto.menu.SystemRouteDTO;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.AddMenuReqVO;
import com.wick.boot.module.system.model.vo.menu.QueryMenuListReqVO;
import com.wick.boot.module.system.model.vo.menu.UpdateMenuReqVO;
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
 * 系统管理 - 菜单信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@RestController
@RequestMapping("/menus")
@Api(tags = "系统管理 - 菜单信息")
public class SystemMenuController {

    @Resource
    private ISystemMenuService systemMenuService;

    @PostMapping
    @ApiOperation(value = "新增菜单信息", notes = "菜单信息")
    @PreAuthorize("@ss.hasPerm('sys:menu:add')")
    public ResultUtil<Long> addMenu(@Valid @RequestBody AddMenuReqVO reqVO) {
        systemMenuService.addMenu(reqVO);
        return ResultUtil.success();
    }

    @PutMapping
    @ApiOperation(value = "编辑菜单信息", notes = "菜单信息")
    @PreAuthorize("@ss.hasPerm('sys:menu:edit')")
    public ResultUtil<Boolean> updateMenu(@Valid @RequestBody UpdateMenuReqVO reqVO) {
        systemMenuService.updateMenu(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除菜单信息", notes = "菜单信息")
    @PreAuthorize("@ss.hasPerm('sys:menu:delete')")
    @ApiImplicitParam(name = "ids", value = "菜单数据ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> deleteMenu(@NotEmpty(message = "菜单主键不能为空")
                                       @PathVariable("ids") List<Long> ids) {
        systemMenuService.deleteMenu(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过菜单ID获取菜单数据", notes = "菜单信息")
    @ApiImplicitParam(name = "id", value = "菜单ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemMenu> getMenuById(@NotNull(message = "菜单ID不能为空")
                                                   @PathVariable("id") Long id) {
        return ResultUtil.success(systemMenuService.getMenuById(id));
    }

    @GetMapping("/options")
    @ApiOperation(value = "获取菜单选项", notes = "菜单信息")
    public ResultUtil<List<SystemMenuOptionsDTO>> options(Boolean onlyParent) {
        return ResultUtil.success(systemMenuService.options(onlyParent));
    }

    @GetMapping
    @ApiOperation(value = "获取菜单列表", notes = "菜单信息")
    public ResultUtil<List<SystemMenuDTO>> listMenus(QueryMenuListReqVO queryParams) {
        return ResultUtil.success(systemMenuService.listMenus(queryParams));
    }

    @GetMapping("/routes")
    @ApiOperation(value = "路由列表", notes = "菜单信息")
    public ResultUtil<List<SystemRouteDTO>> listRoutes() {
        return ResultUtil.success(systemMenuService.listRoutes());
    }

}
