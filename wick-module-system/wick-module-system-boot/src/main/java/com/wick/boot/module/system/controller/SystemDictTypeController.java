package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.dto.dicttype.SystemDictTypeDTO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeAddVO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeQueryVO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeUpdateVO;
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
@RequestMapping("/system/dict-type")
@Api(tags = "字典信息")
public class SystemDictTypeController {

    @Resource
    private SystemDictTypeService dictTypeService;

    @PostMapping("/add")
    @ApiOperation(value = "新增字典类型数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict-type:add')")
    public ResultUtil<Long> add(@Valid @RequestBody SystemDictTypeAddVO reqVO) {
        return ResultUtil.success(dictTypeService.addDictType(reqVO));
    }

    @PutMapping("/update")
    @ApiOperation(value = "编辑字典类型数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict-type:update')")
    public ResultUtil<Boolean> update(@Valid @RequestBody SystemDictTypeUpdateVO reqVO) {
        dictTypeService.updateDictType(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除字典类型数据" , notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict-type:delete')")
    @ApiImplicitParam(name = "ids" , value = "字典类型ID" , required = true, dataType = "Long" , dataTypeClass = Long.class)
    public ResultUtil<Long> removeSystemDictType(@NotEmpty(message = "字典类型主键不能为空") @PathVariable List<Long> ids) {
        dictTypeService.removeSystemDictType(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('system:dict-type:query')")
    @ApiOperation(value = "获取字典类型数据" , notes = "字典信息")
    @ApiImplicitParam(name = "id" , value = "字典类型ID" , required = true, dataType = "Long" , dataTypeClass = Long.class)
    public ResultUtil<SystemDictTypeDTO> getSystemDictType(@NotNull(message = "字典类型主键不能为空") @PathVariable("id") Long id) {
        return ResultUtil.success(dictTypeService.getSystemDictType(id));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('system:dict-type:query')")
    @ApiOperation(value = "获取字典类型分页" , notes = "字典信息")
    public ResultUtil<PageResult<SystemDictTypeDTO>> getSystemDictTypePage(@Valid SystemDictTypeQueryVO reqVO) {
        return ResultUtil.success(dictTypeService.getSystemDictTypePage(reqVO));
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('system:dict-type:options')")
    @ApiOperation(value = "获取字典类型选项" , notes = "字典信息")
    public ResultUtil<List<SystemDictOptionsDTO<String>>> getSystemDictTypeList() {
        return ResultUtil.success(dictTypeService.getSystemDictTypeList());
    }

}
