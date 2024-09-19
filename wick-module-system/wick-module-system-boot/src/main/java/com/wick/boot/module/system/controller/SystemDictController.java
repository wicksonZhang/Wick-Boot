package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.dto.dict.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.dto.dict.data.SystemDictDataDTO;
import com.wick.boot.module.system.model.dto.dict.type.SystemDictTypeDTO;
import com.wick.boot.module.system.model.vo.dict.data.SystemDictDataAddVO;
import com.wick.boot.module.system.model.vo.dict.data.SystemDictDataQueryVO;
import com.wick.boot.module.system.model.vo.dict.data.SystemDictDataUpdateVO;
import com.wick.boot.module.system.model.vo.dict.type.SystemDictTypeAddVO;
import com.wick.boot.module.system.model.vo.dict.type.SystemDictTypeQueryVO;
import com.wick.boot.module.system.model.vo.dict.type.SystemDictTypeUpdateVO;
import com.wick.boot.module.system.service.SystemDictDataService;
import com.wick.boot.module.system.service.SystemDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 后台管理 - 字典管理
 *
 * @author Wickson
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "字典信息")
public class SystemDictController {

    @Resource
    private SystemDictTypeService dictTypeService;

    @Resource
    private SystemDictDataService dictDataService;

    // =============================================== 字典类型 =============================================

    @PostMapping("/types/add")
    @ApiOperation(value = "新增字典类型数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict_type:add')")
    public ResultUtil<Long> add(@Valid @RequestBody SystemDictTypeAddVO reqVO) {
        return ResultUtil.success(dictTypeService.addDictType(reqVO));
    }

    @PutMapping("/types/update")
    @ApiOperation(value = "编辑字典类型数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict_type:update')")
    public ResultUtil<Boolean> update(@Valid @RequestBody SystemDictTypeUpdateVO reqVO) {
        dictTypeService.updateDictType(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/types/{ids}")
    @ApiOperation(value = "删除字典类型数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict_type:delete')")
    @ApiImplicitParam(name = "ids" , value = "字典类型ID" , required = true, dataType = "Long" , dataTypeClass = Long.class)
    public ResultUtil<Long> removeSystemDictType(@NotEmpty(message = "字典类型主键不能为空") @PathVariable List<Long> ids) {
        dictTypeService.removeSystemDictType(ids);
        return ResultUtil.success();
    }

    @GetMapping("/types/{id}")
    @ApiOperation(value = "获取字典类型数据ById" , notes = "字典信息")
    @ApiImplicitParam(name = "id" , value = "字典类型ID" , required = true, dataType = "Long" , dataTypeClass = Long.class)
    public ResultUtil<SystemDictTypeDTO> getSystemDictType(@NotNull(message = "字典类型主键不能为空") @PathVariable("id") Long id) {
        return ResultUtil.success(dictTypeService.getSystemDictType(id));
    }

    @GetMapping("/types/page")
    @ApiOperation(value = "获取字典类型分页" , notes = "字典信息")
    public ResultUtil<PageResult<SystemDictTypeDTO>> getSystemDictTypePage(@Valid SystemDictTypeQueryVO reqVO) {
        return ResultUtil.success(dictTypeService.getSystemDictTypePage(reqVO));
    }

    @ApiOperation(value = "获取字典列表" , notes = "字典信息")
    @GetMapping("/types/list")
    public ResultUtil<List<SystemDictOptionsDTO<String>>> getSystemDictTypeList() {
        return ResultUtil.success(dictTypeService.getSystemDictTypeList());
    }

    //  ============================================ 字典数据 ===============================================

    @PostMapping("/data")
    @ApiOperation(value = "新增字典数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict_data:add')")
    public ResultUtil<Long> add(@Valid @RequestBody SystemDictDataAddVO reqVO) {
        return ResultUtil.success(dictDataService.add(reqVO));
    }

    @PutMapping("/data/update")
    @ApiOperation(value = "编辑字典数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict_data:update')")
    public ResultUtil<Boolean> update(@Valid @RequestBody SystemDictDataUpdateVO reqVO) {
        dictDataService.update(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/data/{ids}")
    @ApiOperation(value = "删除字典数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict_data:delete')")
    @ApiImplicitParam(name = "ids" , value = "字典数据ID" , required = true, dataType = "Long" , dataTypeClass = Long.class)
    public ResultUtil<Long> removeSystemDictData(@NotEmpty(message = "字典类型主键不能为空") @PathVariable List<Long> ids) {
        dictDataService.removeSystemDictData(ids);
        return ResultUtil.success();
    }

    @GetMapping("/data/{id}")
    @ApiOperation(value = "获取字典数据ById" , notes = "字典信息")
    @ApiImplicitParam(name = "id" , value = "字典数据ID" , required = true, dataType = "Long" , dataTypeClass = Long.class)
    public ResultUtil<SystemDictDataDTO> getSystemDictData(@NotNull(message = "字典数据ID不能为空") @PathVariable Long id) {
        return ResultUtil.success(dictDataService.getSystemDictData(id));
    }

    @GetMapping("/data/{typeCode}/options")
    @ApiImplicitParam(name = "typeCode", value = "字典类型编码", required = true, dataTypeClass = String.class)
    public ResultUtil<List<SystemDictOptionsDTO<String>>> getSystemDictDataListOptions(@PathVariable String typeCode) {
        return ResultUtil.success(dictDataService.getSystemDictDataListOptions(typeCode));
    }

    @GetMapping("/data/page")
    @ApiOperation(value = "获取字典数据分页" , notes = "字典信息")
    public ResultUtil<PageResult<SystemDictDataDTO>> getSystemDictDataPage(@Valid SystemDictDataQueryVO reqVO) {
        return ResultUtil.success(dictDataService.getSystemDictDataPage(reqVO));
    }

}
