package com.wick.module.system.controller;

import com.wick.module.system.app.service.ISystemUserService;
import com.wick.module.system.model.dto.SystemUserDTO;
import com.wick.module.system.model.dto.SystemUserInfoDTO;
import com.wick.module.system.model.vo.QueryUserPageReqVO;
import com.wick.common.core.result.PageResult;
import com.wick.common.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统管理 - 用户信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@RestController
@RequestMapping("/users")
@Api(tags = "系统管理 - 用户信息")
public class SystemUserController {

    @Resource
    private ISystemUserService userService;

    @GetMapping("/getUserInfo/{username}")
    @ApiOperation(value = "获取用户信息", notes = "用户信息")
    public ResultUtil<SystemUserDTO> getUserInfo(@Parameter(description = "用户账号")
                                                 @PathVariable("username") String username) {
        return ResultUtil.success(userService.getUserInfo(username));
    }

    @GetMapping("/me")
    @ApiOperation(value = "获取当前登录用户信息", notes = "用户信息")
    public ResultUtil<SystemUserInfoDTO> getCurrentUserInfo() {
        return ResultUtil.success(userService.getCurrentUserInfo());
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取用户分页", notes = "用户信息")
    public ResultUtil<PageResult<SystemUserDTO>> getUserPage(@Valid QueryUserPageReqVO reqVO) {
        return ResultUtil.success(userService.getUserPage(reqVO));
    }

}
