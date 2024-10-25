package com.wick.boot.module.system.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictDataDTO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataAddVO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataQueryVO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataUpdateVO;
import com.wick.boot.module.system.service.SystemDictDataService;
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
 * 字典数据管理-控制类
 *
 * @author Wickson
 * @date 2024-10-15
 */
@RestController
@RequestMapping("/system/dict-data")
@Api(tags = "字典数据")
public class SystemDictDataController {

    @Resource
    private SystemDictDataService dictDataService;

    @PostMapping("/add")
    @ApiOperation(value = "新增字典数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict-data:add')")
    public ResultUtil<Long> add(@Valid @RequestBody SystemDictDataAddVO reqVO) {
        return ResultUtil.success(dictDataService.add(reqVO));
    }

    @PutMapping("/update")
    @ApiOperation(value = "编辑字典数据", notes = "字典信息")
    @PreAuthorize("@ss.hasPerm('system:dict-data:update')")
    public ResultUtil<Boolean> update(@Valid @RequestBody SystemDictDataUpdateVO reqVO) {
        dictDataService.update(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('system:dict-data:delete')")
    @ApiOperation(value = "删除字典数据", notes = "字典信息")
    @ApiImplicitParam(name = "ids", value = "字典数据ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> removeSystemDictData(@NotEmpty(message = "字典类型主键不能为空") @PathVariable List<Long> ids) {
        dictDataService.removeSystemDictData(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('system:dict-data:query')")
    @ApiOperation(value = "获取字典数据", notes = "字典信息")
    @ApiImplicitParam(name = "id", value = "字典数据ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<SystemDictDataDTO> getSystemDictData(@NotNull(message = "字典数据ID不能为空") @PathVariable Long id) {
        return ResultUtil.success(dictDataService.getSystemDictData(id));
    }

    @GetMapping("/{typeCode}/options")
    @PreAuthorize("@ss.hasPerm('system:dict-data:options')")
    @ApiImplicitParam(name = "typeCode", value = "获取字典数据选项", required = true, dataTypeClass = String.class)
    public ResultUtil<List<SystemDictOptionsDTO<String>>> getSystemDictDataListOptions(@PathVariable String typeCode) {
        return ResultUtil.success(dictDataService.getSystemDictDataListOptions(typeCode));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('system:dict-data:query')")
    @ApiOperation(value = "获取字典数据分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictDataDTO>> getSystemDictDataPage(@Valid SystemDictDataQueryVO reqVO) {
        return ResultUtil.success(dictDataService.getSystemDictDataPage(reqVO));
    }

}
