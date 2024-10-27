package com.wick.boot.module.monitor.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.monitor.model.dto.online.MonitorOnlineDTO;
import com.wick.boot.module.monitor.model.vo.online.MonitorOnlineQueryVO;
import com.wick.boot.module.monitor.service.MonitorOnlineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 系统监控-在线用户
 *
 * @author Wickson
 * @date 2024-10-25
 */
@RestController
@RequestMapping("/monitor/online")
@Api(value = "/online", tags = "03-系统监控-在线用户")
public class MonitorOnlineController {

    @Resource
    private MonitorOnlineService onlineService;

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('monitor:online:query')")
    @ApiOperation(value = "获取_在线用户分页", notes = "在线用户", httpMethod = "GET")
    public ResultUtil<PageResult<MonitorOnlineDTO>> getMonitorOnlinePage(@Valid MonitorOnlineQueryVO queryVO) {
        return ResultUtil.success(onlineService.getMonitorOnlinePage(queryVO));
    }

    @DeleteMapping("/force-quit/{sessionId}")
    @PreAuthorize("@ss.hasPerm('monitor:online:force-quit')")
    @ApiOperation(value = "强制退出", notes = "在线用户", httpMethod = "DELETE")
    public ResultUtil<Boolean> forceQuit(@PathVariable String sessionId) {
        onlineService.forceQuit(sessionId);
        return ResultUtil.success();
    }

    @GetMapping("/export")
    @PreAuthorize("@ss.hasPerm('monitor:online:export')")
    @ApiOperation(value = "导出_在线用户分页", notes = "在线用户")
    public void exportOnline(@Valid MonitorOnlineQueryVO queryParams, HttpServletResponse response) {
        onlineService.exportOnline(queryParams, response);
    }

}
