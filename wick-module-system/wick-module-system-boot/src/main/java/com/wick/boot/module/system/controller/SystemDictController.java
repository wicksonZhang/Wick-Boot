package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.app.service.ISystemDictDataService;
import com.wick.boot.module.system.app.service.ISystemDictTypeService;
import com.wick.boot.module.system.model.dto.SystemDictDataDTO;
import com.wick.boot.module.system.model.dto.SystemDictTypeDTO;
import com.wick.boot.module.system.model.vo.dict.data.AddDictDataReqVO;
import com.wick.boot.module.system.model.vo.dict.data.QueryDictDataPageReqVO;
import com.wick.boot.module.system.model.vo.dict.type.AddDictTypeReqVO;
import com.wick.boot.module.system.model.vo.dict.type.QueryDictTypePageReqVO;
import com.wick.boot.module.system.model.vo.dict.type.UpdateDictTypeReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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

    // =============================================== 字典类型 =============================================

    @PostMapping("/types/add")
    @ApiOperation(value = "新增字典类型数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:add')")
    public ResultUtil<Long> addDictType(@Valid @RequestBody AddDictTypeReqVO reqVO) {
        return ResultUtil.success(dictTypeService.addDictType(reqVO));
    }

    // TODO 修改前端传递参数
    @PutMapping("/types/update")
    @ApiOperation(value = "编辑字典类型数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:edit')")
    public ResultUtil<Boolean> updateDictType(@Valid @RequestBody UpdateDictTypeReqVO reqVO) {
        dictTypeService.updateDictType(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/types/{ids}")
    @ApiOperation(value = "删除字典类型数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:delete')")
    @ApiImplicitParam(name = "ids", value = "字典类型ID", required = true)
    public ResultUtil<Long> deleteDictType(@NotEmpty(message = "字典类型主键不能为空")
                                           @PathVariable("ids") List<Long> ids) {
        dictTypeService.deleteDictType(ids);
        return ResultUtil.success();
    }

    @GetMapping("/types/page")
    @ApiOperation(value = "获取字典类型分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictTypeDTO>> getDictTypePage(@Valid QueryDictTypePageReqVO reqVO) {
        return ResultUtil.success(dictTypeService.getDictTypePage(reqVO));
    }

    //  ============================================ 字典数据 ===============================================

    @PostMapping("/data")
    @ApiModelProperty(value = "新增字典数据", notes = "字典信息")
    public ResultUtil<Long> addDictData(@Valid @RequestBody AddDictDataReqVO reqVO) {
        return ResultUtil.success(dictDataService.addDictData(reqVO));
    }

    @GetMapping("/data/page")
    @ApiOperation(value = "获取字典数据分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictDataDTO>> getDictDataPage(@Valid QueryDictDataPageReqVO reqVO) {
        return ResultUtil.success(dictDataService.getDictDataPage(reqVO));
    }


}
