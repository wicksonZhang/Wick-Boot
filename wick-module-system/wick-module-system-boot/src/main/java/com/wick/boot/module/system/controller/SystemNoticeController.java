package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.dto.notice.SystemNoticeDTO;
import com.wick.boot.module.system.model.dto.notice.SystemNoticeDetailDTO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeAddVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeQueryVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeUpdateVO;
import com.wick.boot.module.system.service.SystemNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 系统公告信息
 */
@RestController
@RequestMapping("/system/notice")
@Api(tags = "01-系统管理-公告信息")
public class SystemNoticeController {

    @Resource
    private SystemNoticeService systemNoticeService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('system:notice:add')")
    @ApiOperation(value = "新增_通知公告接口", notes = "通知公告管理")
    public ResultUtil<Long> add(@Valid @RequestBody SystemNoticeAddVO reqVO) {
        this.systemNoticeService.addSystemNotice(reqVO);
        return ResultUtil.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('system:notice:update')")
    @ApiOperation(value = "修改_通知公告接口", notes = "通知公告管理")
    public ResultUtil<Boolean> update(@Valid @RequestBody SystemNoticeUpdateVO reqVO) {
        this.systemNoticeService.updateSystemNotice(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('system:notice:delete')")
    @ApiOperation(value = "删除_通知公告接口", notes = "通知公告管理")
    @ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> remove(@PathVariable("ids") List<Long> ids) {
        this.systemNoticeService.deleteSystemNotice(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('system:notice:query')")
    @ApiOperation(value = "按ID查询_通知公告接口", notes = "通知公告管理")
    @ApiImplicitParam(name = "id", value = "通知公告ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemNoticeDTO> getSystemNotice(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return ResultUtil.success(systemNoticeService.getSystemNotice(id));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('system:notice:query')")
    @ApiOperation(value = "分页查询_通知公告接口", notes = "通知公告管理")
    public ResultUtil<PageResult<SystemNoticeDTO>> getSystemNoticePage(@Valid SystemNoticeQueryVO reqVO) {
        return ResultUtil.success(systemNoticeService.getSystemNoticePage(reqVO));
    }

    @GetMapping("/my-page")
    @PreAuthorize("@ss.hasPerm('system:notice:query')")
    @ApiOperation(value = "分页查询_通知公告接口", notes = "通知公告管理")
    public ResultUtil<Map<String, Object>> getSystemNoticeMyPage(@Valid SystemNoticeQueryVO reqVO) {
        return ResultUtil.success(systemNoticeService.getSystemNoticeMyPage(reqVO));
    }

    @GetMapping("/{id}/detail")
    @PreAuthorize("@ss.hasPerm('system:notice:query')")
    @ApiOperation(value = "通知详细公告接口", notes = "通知公告管理")
    public ResultUtil<SystemNoticeDetailDTO> getSystemNoticeDetail(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return ResultUtil.success(systemNoticeService.getSystemNoticeDetail(id));
    }



}
