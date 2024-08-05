package com.wick.boot.module.tools.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.tools.app.service.IToolCodeGenTableService;
import com.wick.boot.module.tools.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tools.model.vo.table.QueryToolCodeGenTablePageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码自动生成器-Controller
 *
 * @author ZhangZiHeng
 * @date 2024-07-12
 */
@RestController
@RequestMapping("/code-gen")
@Api(tags = "系统管理 - 代码生成器")
public class ToolCodeGenTableController {

    @Resource
    private IToolCodeGenTableService codeGenService;

    @GetMapping("/db/list")
    @ApiOperation(value = "获取数据表", notes = "系统管理 - 代码生成器")
    public ResultUtil<PageResult<ToolCodeGenTablePageReqsDTO>> dataList(@Valid QueryToolCodeGenTablePageReqVO queryVO) {
        return ResultUtil.success(codeGenService.selectDbTableList(queryVO));
    }

    @PostMapping("/importTable/{tableNames}")
    @ApiImplicitParam(name = "tableName", value = "数据表名称", required = true, dataType = "String", dataTypeClass = String.class)
    @ApiOperation(value = "导入数据表", notes = "系统管理 - 代码生成器")
    public ResultUtil<Boolean> importTable(@NotEmpty(message = "表名不能为空")
                                           @PathVariable("tableNames") List<String> tableNames) {
        codeGenService.importTable(tableNames);
        return ResultUtil.success(true);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取代码生成器分页数据", notes = "系统管理 - 代码生成器")
    public ResultUtil<PageResult<ToolCodeGenTablePageReqsDTO>> list(@Valid QueryToolCodeGenTablePageReqVO queryVO) {
        return ResultUtil.success(codeGenService.selectCodeGenTableList(queryVO));
    }

    @GetMapping("/details/{tableId}")
    @ApiOperation(value = "获取代码生成器详细数据", notes = "系统管理 - 代码生成器")
    @ApiImplicitParam(name = "tableId", value = "数据表Id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<ToolCodeGenDetailDTO> getDetails(@PathVariable("tableId") Long tableId) {
        return  ResultUtil.success(codeGenService.getDetails(tableId));
    }


}
