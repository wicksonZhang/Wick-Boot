package cn.wickson.security.system.controller;

import cn.wickson.security.commons.result.ResultUtil;
import cn.wickson.security.system.app.service.ISystemUserService;
import cn.wickson.security.system.model.dto.SystemUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统管理 - 用户信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@RestController
@RequestMapping("/user")
@Api(tags = "系统管理 - 用户信息")
public class SystemUserController {

    @Resource
    private ISystemUserService userService;

    @GetMapping("/getUserInfo/{username}")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public ResultUtil<SystemUserDTO> getUserInfo(@PathVariable("username") String username) {
        return ResultUtil.success(userService.getUserInfo(username));
    }

}
