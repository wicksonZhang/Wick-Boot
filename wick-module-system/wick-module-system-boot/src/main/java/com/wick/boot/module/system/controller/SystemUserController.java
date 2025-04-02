package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.dto.user.SystemUserDTO;
import com.wick.boot.module.system.model.dto.user.SystemUserLoginInfoDTO;
import com.wick.boot.module.system.model.dto.user.SystemUserOptionDTO;
import com.wick.boot.module.system.model.vo.user.SystemUserAddVO;
import com.wick.boot.module.system.model.vo.user.SystemUserQueryVO;
import com.wick.boot.module.system.model.vo.user.SystemUserUpdateVO;
import com.wick.boot.module.system.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
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
 * @author Wickson
 * @date 2024-04-02
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "01-系统管理-用户信息")
public class SystemUserController {

    @Resource
    private SystemUserService userService;

    @GetMapping("/getUserInfo/{username}")
    @ApiOperation(value = "获取_用户信息", notes = "用户信息")
    @ApiImplicitParam(name = "username", value = "用户名称", required = true, dataType = "String", dataTypeClass = String.class)
    public ResultUtil<SystemUserDTO> getUserInfo(@PathVariable String username) {
        return ResultUtil.success(userService.getUserInfo(username));
    }

    @GetMapping("/me")
    @ApiOperation(value = "获取_当前登录用户信息", notes = "用户信息")
    public ResultUtil<SystemUserLoginInfoDTO> getCurrentUserInfo() {
        return ResultUtil.success(userService.getCurrentUserInfo());
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('system:user:query')")
    @ApiOperation(value = "获取_用户分页", notes = "用户信息")
    public ResultUtil<PageResult<SystemUserDTO>> getSystemUserPage(@Valid SystemUserQueryVO reqVO) {
        return ResultUtil.success(userService.getSystemUserPage(reqVO));
    }

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('system:user:add')")
    @ApiOperation(value = "新增_用户信息", notes = "用户信息")
    public ResultUtil<Long> add(@Valid @RequestBody SystemUserAddVO reqVO) {
        return ResultUtil.success(userService.addSystemUser(reqVO));
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('system:user:update')")
    @ApiOperation(value = "修改_用户信息", notes = "用户信息")
    public ResultUtil<Boolean> update(@Valid @RequestBody SystemUserUpdateVO reqVO) {
        userService.updateSystemUser(reqVO);
        return ResultUtil.success();
    }

    @ApiOperation(value = "启用(停用)_用户信息接口", notes = "定时任务管理管理")
    @PatchMapping("/updateStatus/{id}/{status}")
    public ResultUtil<Long> updateStatus(@NotNull(message = "定时任务编号不能为空") @PathVariable Integer id,
                                         @NotNull(message = "调度状态不能为空") @PathVariable Integer status) {
        this.userService.updateStatus(id, status);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('system:user:query')")
    @ApiOperation(value = "获取_用户信息", notes = "用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemUserDTO> getSystemUser(@PathVariable("id") Long id) {
        return ResultUtil.success(userService.getSystemUser(id));
    }

    @GetMapping("/options")
    @PreAuthorize("@ss.hasPerm('system:user:query')")
    @ApiOperation(value = "获取_用户信息列表", notes = "用户信息")
    public ResultUtil<List<SystemUserOptionDTO>> SystemUserOption() {
        return ResultUtil.success(userService.getSystemUserOption());
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('system:user:delete')")
    @ApiOperation(value = "删除_用户信息", notes = "用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Boolean> remove(@PathVariable("ids") List<Long> ids) {
        userService.deleteSystemUser(ids);
        return ResultUtil.success();
    }

    @PatchMapping("/resetPwd/{userId}/{password}")
    @PreAuthorize("@ss.hasPerm('system:user:reset_pwd')")
    @ApiOperation(value = "重置密码", notes = "用户信息")
    public ResultUtil<Boolean> resetPwd(@PathVariable @NotNull(message = "用户id不能为空") Long userId,
                                        @PathVariable @Length(min = 6, max = 16, message = "密码长度为 6-16 位") String password) {
        userService.resetPwd(userId, password);
        return ResultUtil.success();
    }

    @GetMapping("/template")
    @ApiOperation(value = "下载_用户导入模板", notes = "用户信息")
    public void downloadTemplate(HttpServletResponse response) {
        userService.downloadTemplate(response);
    }

    @GetMapping("/export")
    @PreAuthorize("@ss.hasPerm('system:user:export')")
    @ApiOperation(value = "导出_用户信息", notes = "用户信息")
    public void exportSystemUser(@Valid SystemUserQueryVO queryParams, HttpServletResponse response) {
        userService.exportSystemUser(queryParams, response);
    }

    @PostMapping("/import")
    @PreAuthorize("@ss.hasPerm('system:user:import')")
    @ApiOperation(value = "导入_用户信息", notes = "用户信息")
    @ApiImplicitParam(name = "deptId", value = "部门ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Boolean> importSystemUser(@NotNull(message = "部门Id不能为空") Long deptId, MultipartFile file) {
        userService.importSystemUser(deptId, file);
        return ResultUtil.success();
    }

}
