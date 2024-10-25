package com.wick.boot.module.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.wick.boot.common.log.annotations.OperateLog;
import com.wick.boot.module.auth.service.IAuthService;
import com.wick.boot.module.system.model.dto.AuthUserLoginRespDTO;
import com.wick.boot.module.system.model.dto.CaptchaImageRespDTO;
import com.wick.boot.module.system.model.vo.AuthUserLoginReqVO;
import com.wick.boot.common.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 后台管理 - 认证中心
 *
 * @author Wickson
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "00-认证中心")
public class AuthController {

    @Resource
    private IAuthService authService;

    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码", notes = "系统管理 - 认证中心")
    public ResultUtil<CaptchaImageRespDTO> getCaptchaImage() {
        return ResultUtil.success(authService.getCaptchaImage());
    }

    @PostMapping("/login")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    @ApiOperation(value = "用户登录", notes = "系统管理 - 认证中心")
    public ResultUtil<AuthUserLoginRespDTO> login(@Valid AuthUserLoginReqVO reqVO) {
        return ResultUtil.success(authService.login(reqVO));
    }

    @DeleteMapping("/logout")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    @ApiOperation(value = "用户登出", notes = "系统管理 - 认证中心")
    public ResultUtil<Boolean> logout(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token);
        }
        return ResultUtil.success();
    }

}
