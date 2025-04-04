package com.wick.boot.module.tool.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.tool.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tool.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tool.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableQueryVO;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableUpdateVO;
import com.wick.boot.module.tool.service.ToolCodeGenTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 代码自动生成器-Controller
 *
 * @author Wickson
 * @date 2024-07-12
 */
@RestController
@RequestMapping("/tool/code-gen")
@Api(value = "/code-gen", tags = "02-系统工具-代码生成")
public class ToolCodeGenTableController {

    @Resource
    private ToolCodeGenTableService codeGenService;

    @GetMapping("/db/list")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:query')")
    @ApiOperation(value = "获取_数据源数据表", notes = "代码生成", httpMethod = "GET")
    public ResultUtil<PageResult<ToolCodeGenTablePageReqsDTO>> dataList(@Valid ToolCodeGenTableQueryVO queryVO) {
        return ResultUtil.success(codeGenService.selectDbTableList(queryVO));
    }

    @PostMapping("/importTable/{tableNames}/{dataSourceId}")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:import')")
    @ApiOperation(value = "导入_数据表", notes = "代码生成", httpMethod = "POST")
    @ApiImplicitParam(name = "tableName", value = "数据表名称", required = true, dataType = "String", dataTypeClass = String.class)
    public ResultUtil<Boolean> importTable(@NotEmpty(message = "表名不能为空") @PathVariable List<String> tableNames,
                                           @NotNull(message = "数据源ID不能为空") @PathVariable Long dataSourceId) {
        codeGenService.importTable(tableNames, dataSourceId);
        return ResultUtil.success(true);
    }

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:query')")
    @ApiOperation(value = "获取_代码生成分页", notes = "代码生成", httpMethod = "GET")
    public ResultUtil<PageResult<ToolCodeGenTablePageReqsDTO>> list(@Valid ToolCodeGenTableQueryVO queryVO) {
        return ResultUtil.success(codeGenService.selectCodeGenTableList(queryVO));
    }

    @GetMapping("/details/{tableId}")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:query')")
    @ApiOperation(value = "获取_代码生成详细数据", notes = "代码生成", httpMethod = "GET")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<ToolCodeGenDetailDTO> getDetails(@PathVariable Long tableId) {
        return ResultUtil.success(codeGenService.getDetails(tableId));
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:update')")
    @ApiOperation(value = "修改_代码生成信息", notes = "代码生成", httpMethod = "PUT")
    public ResultUtil<Boolean> update(@Validated @RequestBody ToolCodeGenTableUpdateVO updateVO) {
        this.codeGenService.update(updateVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:delete')")
    @ApiOperation(value = "删除_代码生成信息", notes = "代码生成", httpMethod = "DELETE")
    public ResultUtil<Boolean> remove(@PathVariable List<Long> ids) {
        this.codeGenService.deleteToolCodeGenTable(ids);
        return ResultUtil.success(true);
    }

    @GetMapping("/preview/{tableId}")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:preview')")
    @ApiOperation(value = "预览代码", notes = "代码生成", httpMethod = "PUT")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<List<ToolCodeGenPreviewDTO>> previewCode(@PathVariable("tableId") Long tableId) {
        return ResultUtil.success(this.codeGenService.previewCode(tableId));
    }

    @PutMapping("/syncDb/{tableId}")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:async')")
    @ApiOperation(value = "同步代码", notes = "代码生成", httpMethod = "PUT")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Boolean> syncDb(@PathVariable("tableId") Long tableId) {
        this.codeGenService.syncDb(tableId);
        return ResultUtil.success(true);
    }

    @GetMapping("/download/{tableId}")
    @PreAuthorize("@ss.hasPerm('tool:code-gen:download')")
    @ApiOperation(value = "生成代码", notes = "代码生成", httpMethod = "PUT")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Boolean> download(HttpServletResponse response, @PathVariable Long tableId) {
        this.codeGenService.download(response, tableId);
        return ResultUtil.success();
    }

}