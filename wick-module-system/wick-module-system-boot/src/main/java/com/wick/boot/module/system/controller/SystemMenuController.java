package com.wick.boot.module.system.controller;

import com.wick.boot.module.system.model.dto.SystemMenuDTO;
import com.wick.boot.module.system.model.dto.SystemRouteDTO;
import com.wick.boot.module.system.model.vo.QueryMenuListReqVO;
import com.wick.boot.module.system.app.service.ISystemMenuService;
import com.wick.boot.common.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @GetMapping
    @ApiOperation(value = "获取菜单列表", notes = "系统管理 - 菜单信息")
    public ResultUtil<List<SystemMenuDTO>> listMenus(QueryMenuListReqVO queryParams) {
        return ResultUtil.success(systemMenuService.listMenus(queryParams));
    }

    @GetMapping("/routes")
    @ApiOperation(value = "路由列表", notes = "系统管理 - 菜单信息")
    public ResultUtil<List<SystemRouteDTO>> listRoutes() {
        return ResultUtil.success(systemMenuService.listRoutes());
    }

}
