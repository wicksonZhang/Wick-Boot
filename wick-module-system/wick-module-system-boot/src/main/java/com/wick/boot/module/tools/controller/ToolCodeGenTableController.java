package com.wick.boot.module.tools.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.tools.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tools.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tools.model.vo.table.ToolCodeGenTableQueryVO;
import com.wick.boot.module.tools.model.vo.table.ToolCodeGenTableUpdateVO;
import com.wick.boot.module.tools.service.ToolCodeGenTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码自动生成器-Controller
 *
 * @author Wickson
 * @date 2024-07-12
 */
@RestController
@RequestMapping("/code-gen")
@Api(value = "/code-gen", tags = "代码生成")
public class ToolCodeGenTableController {

    @Resource
    private ToolCodeGenTableService codeGenService;

    @GetMapping("/db/list")
    @ApiOperation(value = "获取数据表", notes = "代码生成", httpMethod = "GET")
    public ResultUtil<PageResult<ToolCodeGenTablePageReqsDTO>> dataList(@Valid ToolCodeGenTableQueryVO queryVO) {
        return ResultUtil.success(codeGenService.selectDbTableList(queryVO));
    }

    @PostMapping("/importTable/{tableNames}")
    @PreAuthorize("@ss.hasPerm('tools:code-gen:import')")
    @ApiOperation(value = "导入数据表", notes = "代码生成", httpMethod = "POST")
    @ApiImplicitParam(name = "tableName", value = "数据表名称", required = true, dataType = "String", dataTypeClass = String.class)
    public ResultUtil<Boolean> importTable(@NotEmpty(message = "表名不能为空") @PathVariable List<String> tableNames) {
        codeGenService.importTable(tableNames);
        return ResultUtil.success(true);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取代码生成分页数据", notes = "代码生成", httpMethod = "GET")
    public ResultUtil<PageResult<ToolCodeGenTablePageReqsDTO>> list(@Valid ToolCodeGenTableQueryVO queryVO) {
        return ResultUtil.success(codeGenService.selectCodeGenTableList(queryVO));
    }

    @GetMapping("/details/{tableId}")
    @ApiOperation(value = "获取代码生成详细数据", notes = "代码生成", httpMethod = "GET")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<ToolCodeGenDetailDTO> getDetails(@PathVariable Long tableId) {
        return ResultUtil.success(codeGenService.getDetails(tableId));
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('tools:code-gen:update')")
    @ApiOperation(value = "修改代码生成信息", notes = "代码生成", httpMethod = "PUT")
    public ResultUtil<Boolean> update(@Validated @RequestBody ToolCodeGenTableUpdateVO updateVO) {
        this.codeGenService.update(updateVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('tools:code-gen:delete')")
    @ApiOperation(value = "删除代码生成信息", notes = "代码生成", httpMethod = "DELETE")
    public ResultUtil<Boolean> remove(@PathVariable List<Long> ids) {
        this.codeGenService.deleteToolCodeGenTable(ids);
        return ResultUtil.success(true);
    }

    @GetMapping("/preview/{tableId}")
    @ApiOperation(value = "预览代码", notes = "代码生成", httpMethod = "PUT")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<List<ToolCodeGenPreviewDTO>> previewCode(@PathVariable("tableId") Long tableId) {
        return ResultUtil.success(this.codeGenService.previewCode(tableId));
    }

    @PutMapping("/syncDb/{tableId}")
    @PreAuthorize("@ss.hasPerm('tools:code-gen:async')")
    @ApiOperation(value = "同步代码", notes = "代码生成", httpMethod = "PUT")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Boolean> syncDb(@PathVariable("tableId") Long tableId) {
        this.codeGenService.syncDb(tableId);
        return ResultUtil.success(true);
    }

}