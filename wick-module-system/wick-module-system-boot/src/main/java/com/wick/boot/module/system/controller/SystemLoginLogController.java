package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemLoginLogService;
import com.wick.boot.module.system.model.dto.SystemLoginLogDTO;
import com.wick.boot.module.system.model.vo.logger.login.QueryLoginLogPageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    @GetMapping("/page")
    @ApiOperation(value = "获取登录日志分页", notes = "登录日志")
    public ResultUtil<PageResult<SystemLoginLogDTO>> getLoginLogPage(@Valid QueryLoginLogPageReqVO reqVO) {
        return ResultUtil.success(loginLogService.getLoginLogPage(reqVO));
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出登录日志分页", notes = "登录日志")
    public void exportLoginLog(@Valid QueryLoginLogPageReqVO queryParams, HttpServletResponse response) {
        loginLogService.exportLoginLog(queryParams, response);
    }


}
