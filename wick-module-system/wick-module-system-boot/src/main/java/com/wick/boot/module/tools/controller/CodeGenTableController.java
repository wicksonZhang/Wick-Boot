package com.wick.boot.module.tools.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.tools.app.service.ICodeGenTableService;
import com.wick.boot.module.tools.model.dto.CodeGenTableDTO;
import com.wick.boot.module.tools.model.vo.QueryCodeGenTablePageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
public class CodeGenTableController {

    @Resource
    private ICodeGenTableService codeGenService;

    @GetMapping("/db/list")
    @ApiOperation(value = "获取数据表", notes = "系统管理 - 代码生成器")
    public ResultUtil<PageResult<CodeGenTableDTO>> dataList(@Valid QueryCodeGenTablePageReqVO queryVO) {
        return ResultUtil.success(codeGenService.selectDbTableList(queryVO));
    }

    @PostMapping("/importTable/{tableNames}")
    @ApiImplicitParam(name = "tableName", value = "数据表名称", required = true, dataType = "String", dataTypeClass = String.class)
    @ApiOperation(value = "导入数据表", notes = "系统管理 - 代码生成器")
    public ResultUtil<Boolean> importTable(@NotNull @PathVariable("tableNames") List<String> tableNames) {
        codeGenService.importTable(tableNames);
        return ResultUtil.success(true);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取代码生成器分页数据", notes = "系统管理 - 代码生成器")
    public ResultUtil<PageResult<CodeGenTableDTO>> list(@Valid QueryCodeGenTablePageReqVO queryVO) {
        return ResultUtil.success(codeGenService.selectCodeGenTableList(queryVO));
    }



}
