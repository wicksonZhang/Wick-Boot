package com.wick.boot.module.tools.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.tools.service.ToolCodeGenTableService;
import com.wick.boot.module.tools.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tools.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tools.model.vo.table.QueryToolCodeGenTablePageReqVO;
import com.wick.boot.module.tools.model.vo.table.UpdateToolCodeGenReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/code-gen", tags = {"代码自动生成器-Controller"})
@RestController
@RequestMapping("/code-gen")
public class ToolCodeGenTableController {

    @Resource
    private ToolCodeGenTableService codeGenService;

    @ApiOperation(value = "获取数据表", notes = "系统管理 - 代码生成器", httpMethod = "GET")
    @GetMapping("/db/list")
    public ResultUtil<PageResult<ToolCodeGenTablePageReqsDTO>> dataList(@Valid QueryToolCodeGenTablePageReqVO queryVO) {
        return ResultUtil.success(codeGenService.selectDbTableList(queryVO));
    }

    @ApiOperation(value = "导入数据表", notes = "系统管理 - 代码生成器", httpMethod = "POST")
    @PostMapping("/importTable/{tableNames}")
    @ApiImplicitParam(name = "tableName", value = "数据表名称", required = true, dataType = "String", dataTypeClass = String.class)
    public ResultUtil<Boolean> importTable(@NotEmpty(message = "表名不能为空")
                                           @PathVariable("tableNames") List<String> tableNames) {
        codeGenService.importTable(tableNames);
        return ResultUtil.success(true);
    }

    @ApiOperation(value = "获取代码生成器分页数据", notes = "系统管理 - 代码生成器", httpMethod = "GET")
    @GetMapping("/list")
    public ResultUtil<PageResult<ToolCodeGenTablePageReqsDTO>> list(@Valid QueryToolCodeGenTablePageReqVO queryVO) {
        return ResultUtil.success(codeGenService.selectCodeGenTableList(queryVO));
    }

    @ApiOperation(value = "获取代码生成器详细数据", notes = "系统管理 - 代码生成器", httpMethod = "GET")
    @GetMapping("/details/{tableId}")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<ToolCodeGenDetailDTO> getDetails(@PathVariable("tableId") Long tableId) {
        return ResultUtil.success(codeGenService.getDetails(tableId));
    }

    @ApiOperation(value = "修改代码生成器信息", notes = "系统管理 - 代码生成器", httpMethod = "PUT")
    @PutMapping("/update")
    public ResultUtil<Boolean> update(@Validated @RequestBody UpdateToolCodeGenReqVO updateVO) {
        this.codeGenService.update(updateVO);
        return ResultUtil.success(true);
    }

    @ApiOperation(value = "预览代码", notes = "系统管理 - 代码生成器", httpMethod = "PUT")
    @GetMapping("/preview/{tableId}")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<List<ToolCodeGenPreviewDTO>> previewCode(@PathVariable("tableId") Long tableId) {
        return ResultUtil.success(this.codeGenService.previewCode(tableId));
    }

}
