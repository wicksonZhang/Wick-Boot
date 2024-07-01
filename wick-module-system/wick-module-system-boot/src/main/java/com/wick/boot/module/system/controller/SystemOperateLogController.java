package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemOperateLogService;
import com.wick.boot.module.system.model.dto.SystemOperateLogDTO;
import com.wick.boot.module.system.model.vo.logger.operate.QueryOperateLogPageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统管理-操作日志
 *
 * @author ZhangZiHeng
 * @date 2024-07-01
 */
@RestController
@RequestMapping("/operate-log")
@Api(tags = "系统管理 - 操作日志")
public class SystemOperateLogController {

    @Resource
    private ISystemOperateLogService operateLogService;

    @GetMapping("/page")
    @ApiOperation(value = "获取操作日志分页", notes = "操作日志")
    public ResultUtil<PageResult<SystemOperateLogDTO>> getOperateLogPage(@Valid QueryOperateLogPageReqVO reqVO) {
        return ResultUtil.success(operateLogService.getOperateLogPage(reqVO));
    }

}
