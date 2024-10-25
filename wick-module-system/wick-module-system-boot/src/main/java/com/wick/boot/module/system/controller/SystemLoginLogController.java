package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.service.SystemLoginLogService;
import com.wick.boot.module.system.model.dto.logger.login.SystemLoginLogDTO;
import com.wick.boot.module.system.model.vo.logger.login.SystemLoginLogQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 系统登陆日志Controller
 *
 * @author Wickson
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/system/login-log")
@Api(tags = "1-系统管理-登录日志")
public class SystemLoginLogController {

    @Resource
    private SystemLoginLogService loginLogService;

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('system:login-log:query')")
    @ApiOperation(value = "获取_登录日志分页", notes = "登录日志")
    public ResultUtil<PageResult<SystemLoginLogDTO>> getLoginLogPage(@Valid SystemLoginLogQueryVO reqVO) {
        return ResultUtil.success(loginLogService.getLoginLogPage(reqVO));
    }

    @GetMapping("/export")
    @PreAuthorize("@ss.hasPerm('system:login-log:export')")
    @ApiOperation(value = "导出_登录日志分页", notes = "登录日志")
    public void exportLoginLog(@Valid SystemLoginLogQueryVO queryParams, HttpServletResponse response) {
        loginLogService.exportLoginLog(queryParams, response);
    }


}
