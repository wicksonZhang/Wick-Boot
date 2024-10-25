package com.wick.boot.module.tool.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.tool.model.dto.datasource.ToolDataSourceDTO;
import com.wick.boot.module.tool.model.dto.datasource.ToolDataSourceOptionsDTO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceAddVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceQueryVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceUpdateVO;
import com.wick.boot.module.tool.service.ToolDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据源配置管理-控制类
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
@RestController
@RequestMapping("/tool/data-source")
@Api(tags = "02-系统工具-数据源配置")
public class ToolDataSourceController {

    @Resource
    private ToolDataSourceService toolDataSourceService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('tool:data-source:add')")
    @ApiOperation(value = "新增_数据源配置数据", notes = "数据源配置管理")
    public ResultUtil<Long> add(@Valid @RequestBody ToolDataSourceAddVO reqVO) {
        this.toolDataSourceService.addToolDataSource(reqVO);
        return ResultUtil.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('tool:data-source:update')")
    @ApiOperation(value = "修改_数据源配置数据", notes = "数据源配置管理")
    public ResultUtil<Boolean> update(@Valid @RequestBody ToolDataSourceUpdateVO reqVO) {
        this.toolDataSourceService.updateToolDataSource(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('tool:data-source:delete')")
    @ApiOperation(value = "删除_数据源配置数据", notes = "数据源配置管理")
    @ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> remove(@PathVariable("ids") List<Long> ids) {
        this.toolDataSourceService.deleteToolDataSource(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('tool:data-source:query')")
    @ApiOperation(value = "获取_数据源配置数据", notes = "数据源配置管理")
    @ApiImplicitParam(name = "id", value = "数据源配置ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<ToolDataSourceDTO> getToolDataSource(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return ResultUtil.success(toolDataSourceService.getToolDataSource(id));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('tool:data-source:query')")
    @ApiOperation(value = "获取_数据源配置分页数据", notes = "数据源配置管理")
    public ResultUtil<PageResult<ToolDataSourceDTO>> getToolDataSourcePage(@Valid ToolDataSourceQueryVO reqVO) {
        return ResultUtil.success(toolDataSourceService.getToolDataSourcePage(reqVO));
    }

    @PostMapping("/testConnection")
    @PreAuthorize("@ss.hasPerm('tool:data-source:query')")
    @ApiOperation(value = "测试连接_数据源接口", notes = "数据源配置")
    public ResultUtil<Boolean> testConnection(@Valid @RequestBody ToolDataSourceAddVO reqVO) {
        return ResultUtil.success(toolDataSourceService.testConnection(reqVO));
    }

    @GetMapping("/options")
    @PreAuthorize("@ss.hasPerm('tool:data-source:query')")
    @ApiOperation(value = "列表查询_数据源接口", notes = "数据源配置")
    public ResultUtil<List<ToolDataSourceOptionsDTO>> options() {
        return ResultUtil.success(toolDataSourceService.options());
    }
}