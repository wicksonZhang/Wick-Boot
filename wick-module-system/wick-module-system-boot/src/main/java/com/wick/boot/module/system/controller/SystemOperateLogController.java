package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.vo.logger.operate.SystemOperateLogQueryVO;
import com.wick.boot.module.system.service.SystemOperateLogService;
import com.wick.boot.module.system.model.dto.logger.operate.SystemOperateLogDTO;
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
 * 系统管理-操作日志
 *
 * @author Wickson
 * @date 2024-07-01
 */
@RestController
@RequestMapping("/system/operate-log")
@Api(tags = "1-系统管理-操作日志")
public class SystemOperateLogController {

    @Resource
    private SystemOperateLogService operateLogService;

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('system:operate-log:query')")
    @ApiOperation(value = "获取_操作日志分页", notes = "操作日志")
    public ResultUtil<PageResult<SystemOperateLogDTO>> getOperateLogPage(@Valid SystemOperateLogQueryVO reqVO) {
        return ResultUtil.success(operateLogService.getOperateLogPage(reqVO));
    }

    @GetMapping("/export")
    @PreAuthorize("@ss.hasPerm('system:operate-log:export')")
    @ApiOperation(value = "导出_操作日志分页", notes = "登录日志")
    public void exportOperateLog(@Valid SystemOperateLogQueryVO queryParams, HttpServletResponse response) {
        operateLogService.exportOperateLog(queryParams, response);
    }

}
