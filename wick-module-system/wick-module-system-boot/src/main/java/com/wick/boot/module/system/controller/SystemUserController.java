package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemUserService;
import com.wick.boot.module.system.model.dto.user.SystemUserDTO;
import com.wick.boot.module.system.model.dto.user.SystemUserInfoDTO;
import com.wick.boot.module.system.model.vo.user.AddUserVO;
import com.wick.boot.module.system.model.vo.user.QueryUserPageReqVO;
import com.wick.boot.module.system.model.vo.user.UpdateUserPwdVO;
import com.wick.boot.module.system.model.vo.user.UpdateUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemUserDTO> getUserById(@PathVariable("id") Long id) {
        return ResultUtil.success(userService.getUserById(id));
    }

    @PutMapping
    @PreAuthorize("@ss.hasPerm('sys:user:edit')")
    @ApiOperation(value = "修改用户信息", notes = "用户信息")
    public ResultUtil<Boolean> updateUser(@Valid @RequestBody UpdateUserVO reqVO) {
        userService.updateUser(reqVO);
        return ResultUtil.success();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:user:del')")
    @ApiOperation(value = "删除用户信息", notes = "用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Boolean> deleteUser(@PathVariable("ids") List<Long> ids) {
        userService.deleteUser(ids);
        return ResultUtil.success();
    }

    @PatchMapping("/resetPwd")
    @PreAuthorize("@ss.hasPerm('sys:user:reset_pwd')")
    @ApiOperation(value = "重置密码", notes = "用户信息")
    public ResultUtil<Boolean> resetPwd(@Valid @RequestBody UpdateUserPwdVO reqVO) {
        userService.resetPwd(reqVO);
        return ResultUtil.success();
    }

    @GetMapping("/simple-list")
    @ApiOperation(value = "获得用户列表", notes = "用户信息")
    public ResultUtil<List<SystemUserDTO>> simpleList() {
        return  ResultUtil.success(userService.simpleList());
    }

    @GetMapping("/template")
    @ApiOperation(value = "用户导入模板下载", notes = "用户信息")
    public void downloadTemplate(HttpServletResponse response) {
        userService.downloadTemplate(response);
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出用户", notes = "用户信息")
    public void exportUsers(@Valid QueryUserPageReqVO queryParams, HttpServletResponse response) {
        userService.exportUsers(queryParams, response);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入用户", notes = "用户信息")
    @ApiImplicitParam(name = "deptId", value = "部门ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Boolean> importUsers(@NotNull(message = "部门Id不能为空") Long deptId, MultipartFile file) {
        userService.importUsers(deptId, file);
        return ResultUtil.success();
    }

}
