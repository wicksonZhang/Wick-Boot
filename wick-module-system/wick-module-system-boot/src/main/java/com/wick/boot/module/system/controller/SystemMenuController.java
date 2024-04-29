package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemMenuService;
import com.wick.boot.module.system.model.dto.SystemMenuDTO;
import com.wick.boot.module.system.model.dto.SystemRouteDTO;
import com.wick.boot.module.system.model.vo.menu.AddMenuReqVO;
import com.wick.boot.module.system.model.vo.menu.QueryMenuListReqVO;
import com.wick.boot.module.system.model.vo.menu.UpdateMenuReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
