package cn.wickson.security.system.controller;

import cn.wickson.security.commons.result.ResultUtil;
import cn.wickson.security.system.app.service.IAuthService;
import cn.wickson.security.system.model.dto.AuthUserLoginRespDTO;
import cn.wickson.security.system.model.dto.CaptchaImageRespDTO;
import cn.wickson.security.system.model.vo.AuthUserLoginReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 后台管理 - 认证中心
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "系统管理 - 认证中心")
public class SystemAuthController {

    @Resource
    private IAuthService authService;

    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码", notes = "系统管理 - 认证中心")
    public ResultUtil<CaptchaImageRespDTO> getCaptchaImage() {
        return ResultUtil.success(authService.getCaptchaImage());
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "系统管理 - 认证中心")
    public ResultUtil<AuthUserLoginRespDTO> login(@Valid AuthUserLoginReqVO reqVO) {
        return ResultUtil.success(authService.login(reqVO));
    }
}
