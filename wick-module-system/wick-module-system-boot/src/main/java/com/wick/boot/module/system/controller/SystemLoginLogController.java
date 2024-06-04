package com.wick.boot.module.system.controller;

import com.wick.boot.module.system.app.service.ISystemLoginLogService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统登陆日志Controller
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/login-log")
@Api(tags = "系统管理 - 登录日志")
public class SystemLoginLogController {

    @Resource
    private ISystemLoginLogService loginLogService;

}
