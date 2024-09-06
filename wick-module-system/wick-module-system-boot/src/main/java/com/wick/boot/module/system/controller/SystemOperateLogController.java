package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.service.SystemOperateLogService;
import com.wick.boot.module.system.model.dto.logger.operate.SystemOperateLogDTO;
import com.wick.boot.module.system.model.vo.logger.operate.QueryOperateLogPageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/operate-log")
@Api(tags = "系统管理 - 操作日志")
public class SystemOperateLogController {

    @Resource
    private SystemOperateLogService operateLogService;

    @GetMapping("/page")
    @ApiOperation(value = "获取操作日志分页", notes = "操作日志")
    public ResultUtil<PageResult<SystemOperateLogDTO>> getOperateLogPage(@Valid QueryOperateLogPageReqVO reqVO) {
        return ResultUtil.success(operateLogService.getOperateLogPage(reqVO));
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出操作日志分页", notes = "登录日志")
    public void exportOperateLog(@Valid QueryOperateLogPageReqVO queryParams, HttpServletResponse response) {
        operateLogService.exportOperateLog(queryParams, response);
    }

}
