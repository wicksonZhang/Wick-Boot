package com.wick.boot.module.system.controller;

import com.wick.boot.module.system.app.service.ISystemUserService;
import com.wick.boot.module.system.model.dto.SystemUserDTO;
import com.wick.boot.module.system.model.dto.SystemUserInfoDTO;
import com.wick.boot.module.system.model.vo.user.AddUserVO;
import com.wick.boot.module.system.model.vo.user.QueryUserPageReqVO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.vo.user.UpdateUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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

    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:user:add')")
    @ApiOperation(value = "新增用户信息", notes = "用户信息")
    public ResultUtil<Long> addUser(@Valid @RequestBody AddUserVO reqVO) {
        userService.addUser(reqVO);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询用户信息", notes = "用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    public ResultUtil<SystemUserDTO> getUserById(@PathVariable("id") Long id) {
        return ResultUtil.success(userService.getUserById(id));
    }

    @PutMapping
    @PreAuthorize("@ss.hasPerm('sys:user:edit')")
    @ApiOperation(value = "修改用户信息", notes = "用户信息")
    public ResultUtil<Boolean> updateUser(@RequestBody UpdateUserVO reqVO) {
        userService.updateUser(reqVO);
        return ResultUtil.success();
    }

}
