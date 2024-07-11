package com.wick.boot.module.tools.controller;

import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.tools.app.service.IDataSourceService;
import com.wick.boot.module.tools.model.dto.DataSourceConfigDTO;
import com.wick.boot.module.tools.model.vo.AddDataSourceConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 数据源-Controller
 *
 * @author ZhangZiHeng
 * @date 2024-07-11
 */
@RestController
@RequestMapping("/data-source-config")
@Api(tags = "系统管理 - 数据源配置")
public class DataSourceConfigController {

    @Resource
    private IDataSourceService dataSourceService;

    @ApiOperation(value = "新增数据源配置", notes = "数据源配置")
    @PostMapping("/add")
//    @PreAuthorize("@ss.hasPerm('sys:data_source_config:add')")
    public ResultUtil<Long> addDataSourceConfig(@Valid @RequestBody AddDataSourceConfigVO reqVO) {
        return ResultUtil.success(dataSourceService.addDataSourceConfig(reqVO));
    }

    @ApiOperation(value = "测试数据源配置是否连接成功", notes = "数据源配置")
    @GetMapping("/testConnection")
//    @PreAuthorize("@ss.hasPerm('sys:data_source_config:add')")
    public ResultUtil<Boolean> testConnection(@Valid @RequestBody AddDataSourceConfigVO reqVO) {
        return ResultUtil.success(dataSourceService.testConnection(reqVO));
    }

    @ApiOperation(value = "获取数据源配置列表", notes = "数据源配置")
    @GetMapping
    public ResultUtil<List<DataSourceConfigDTO>> listDataSource() {
        return ResultUtil.success(dataSourceService.listDataSourceConfig());
    }

}
