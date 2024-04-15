package com.wick.module.system.controller;

import com.wick.module.system.app.service.ISystemRoleService;
import com.wick.module.system.model.dto.SystemRoleDTO;
import com.wick.module.system.model.vo.QueryRolePageReqVO;
import com.wick.common.core.result.PageResult;
import com.wick.common.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @GetMapping("/page")
    @ApiOperation(value = "获取角色分页", notes = "角色信息")
    public ResultUtil<PageResult<SystemRoleDTO>> getUserPage(@Valid QueryRolePageReqVO reqVO) {
        return ResultUtil.success(systemRoleService.getRolePage(reqVO));
    }

}
