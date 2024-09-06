package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.service.SystemDictDataService;
import com.wick.boot.module.system.service.SystemDictTypeService;
import com.wick.boot.module.system.model.dto.dict.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.dto.dict.data.SystemDictDataDTO;
import com.wick.boot.module.system.model.dto.dict.type.SystemDictTypeDTO;
import com.wick.boot.module.system.model.vo.dict.data.AddDictDataReqVO;
import com.wick.boot.module.system.model.vo.dict.data.QueryDictDataPageReqVO;
import com.wick.boot.module.system.model.vo.dict.data.UpdateDictDataReqVO;
import com.wick.boot.module.system.model.vo.dict.type.AddDictTypeReqVO;
import com.wick.boot.module.system.model.vo.dict.type.QueryDictTypePageReqVO;
import com.wick.boot.module.system.model.vo.dict.type.UpdateDictTypeReqVO;
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
@Api(tags = "系统管理 - 字典信息")
public class SystemDictController {

    @Resource
    private SystemDictTypeService dictTypeService;

    @Resource
    private SystemDictDataService dictDataService;

    // =============================================== 字典类型 =============================================

    @PostMapping("/types/add")
    @ApiOperation(value = "新增字典类型数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:add')")
    public ResultUtil<Long> addDictType(@Valid @RequestBody AddDictTypeReqVO reqVO) {
        return ResultUtil.success(dictTypeService.addDictType(reqVO));
    }

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
    @ApiImplicitParam(name = "ids", value = "字典类型ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> deleteDictType(@NotEmpty(message = "字典类型主键不能为空") @PathVariable("ids") List<Long> ids) {
        dictTypeService.deleteDictType(ids);
        return ResultUtil.success();
    }

    @GetMapping("/types/{id}")
    @ApiOperation(value = "获取字典类型数据ById", notes = "字典信息")
    @ApiImplicitParam(name = "id", value = "字典类型ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemDictTypeDTO> getDictType(@NotNull(message = "字典类型主键不能为空") @PathVariable("id") Long id) {
        return ResultUtil.success(dictTypeService.getDictTypeById(id));
    }

    @GetMapping("/types/page")
    @ApiOperation(value = "获取字典类型分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictTypeDTO>> getDictTypePage(@Valid QueryDictTypePageReqVO reqVO) {
        return ResultUtil.success(dictTypeService.getDictTypePage(reqVO));
    }

    @ApiOperation(value = "获取字典列表", notes = "字典信息")
    @GetMapping("/types/list")
    public ResultUtil<List<SystemDictOptionsDTO<String>>> getDictList() {
        return ResultUtil.success(dictTypeService.getDictTypeList());
    }

    //  ============================================ 字典数据 ===============================================

    @PostMapping("/data")
    @ApiOperation(value = "新增字典数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('sys:dict_data:add')")
    public ResultUtil<Long> addDictData(@Valid @RequestBody AddDictDataReqVO reqVO) {
        return ResultUtil.success(dictDataService.addDictData(reqVO));
    }

    @PutMapping("/data/update")
    @ApiOperation(value = "编辑字典数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('sys:dict_data:edit')")
    public ResultUtil<Boolean> updateDictData(@Valid @RequestBody UpdateDictDataReqVO reqVO) {
        dictDataService.updateDictData(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/data/{ids}")
    @ApiOperation(value = "删除字典数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('sys:dict_data:delete')")
    @ApiImplicitParam(name = "ids", value = "字典数据ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> deleteDictData(@NotEmpty(message = "字典类型主键不能为空") @PathVariable("ids") List<Long> ids) {
        dictDataService.deleteDictData(ids);
        return ResultUtil.success();
    }

    @GetMapping("/data/{id}")
    @ApiOperation(value = "获取字典数据ById", notes = "字典信息")
    @ApiImplicitParam(name = "id", value = "字典数据ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemDictDataDTO> getDictData(@NotNull(message = "字典数据ID不能为空") @PathVariable("id") Long id) {
        return ResultUtil.success(dictDataService.getDictData(id));
    }

    @GetMapping("/data/{typeCode}/options")
    @ApiImplicitParam(name = "typeCode", value = "字典类型编码", required = true, dataType = "String", dataTypeClass = String.class)
    public ResultUtil<List<SystemDictOptionsDTO>> listDictOptions(@NotNull(message = "字典类型编码不能为空")
                                                                      @PathVariable("typeCode") String typeCode) {
        return ResultUtil.success(dictDataService.listDictDataOptions(typeCode));
    }

    @GetMapping("/data/page")
    @ApiOperation(value = "获取字典数据分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictDataDTO>> getDictDataPage(@Valid QueryDictDataPageReqVO reqVO) {
        return ResultUtil.success(dictDataService.getDictDataPage(reqVO));
    }

}
