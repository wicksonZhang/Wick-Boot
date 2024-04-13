package com.wick.module.system.controller;

import com.wick.module.system.app.service.ISystemDictDataService;
import com.wick.module.system.app.service.ISystemDictTypeService;
import com.wick.module.system.model.dto.SystemDictDataDTO;
import com.wick.module.system.model.dto.SystemDictTypeDTO;
import com.wick.module.system.model.vo.QueryDictDataPageReqVO;
import com.wick.module.system.model.vo.QueryDictTypePageReqVO;
import com.wick.common.core.result.PageResult;
import com.wick.common.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 后台管理 - 字典管理
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "系统管理 - 字典信息")
public class SystemDictController {

    @Resource
    private ISystemDictTypeService dictTypeService;

    @Resource
    private ISystemDictDataService dictDataService;

    @GetMapping("/types/page")
    @ApiOperation(value = "获取字典类型分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictTypeDTO>> getDictTypePage(@Valid QueryDictTypePageReqVO reqVO) {
        return ResultUtil.success(dictTypeService.getDictTypePage(reqVO));
    }

    @GetMapping("/data/page")
    @ApiOperation(value = "获取字典数据分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictDataDTO>> getDictDataPage(@Valid QueryDictDataPageReqVO reqVO) {
        return ResultUtil.success(dictDataService.getDictDataPage(reqVO));
    }

}
