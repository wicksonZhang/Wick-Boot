package com.wick.boot.module.okx.api.config.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.okx.api.config.model.dto.apiconfig.ApiConfigDTO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigAddVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigQueryVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigUpdateVO;
import com.wick.boot.module.okx.api.config.service.ApiConfigService;
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
 * Api配置管理-控制类
 *
 * @author Wickson
 * @date 2024-11-18 10:48
 */
@RestController
@RequestMapping("/okx/api-config")
@Api(tags = "04-欧意（OKX）-Api配置")
public class ApiConfigController {

    @Resource
    private ApiConfigService apiConfigService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('okx:api-config:add')")
    @ApiOperation(value = "新增_Api配置接口", notes = "Api配置管理")
    public ResultUtil<Long> add(@Valid @RequestBody ApiConfigAddVO reqVO) {
        this.apiConfigService.addApiConfig(reqVO);
        return ResultUtil.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPerm('okx:api-config:update')")
    @ApiOperation(value = "修改_Api配置接口", notes = "Api配置管理")
    public ResultUtil<Boolean> update(@Valid @RequestBody ApiConfigUpdateVO reqVO) {
        this.apiConfigService.updateApiConfig(reqVO);
        return ResultUtil.success(true);
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('okx:api-config:delete')")
    @ApiOperation(value = "删除_Api配置接口", notes = "Api配置管理")
    @ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> remove(@PathVariable("ids") List<Long> ids) {
        this.apiConfigService.deleteApiConfig(ids);
        return ResultUtil.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('okx:api-config:query')")
    @ApiOperation(value = "按ID查询_Api配置接口", notes = "Api配置管理")
    @ApiImplicitParam(name = "id", value = "Api配置ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<ApiConfigDTO> getApiConfig(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return ResultUtil.success(apiConfigService.getApiConfig(id));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('okx:api-config:query')")
    @ApiOperation(value = "分页查询_Api配置接口", notes = "Api配置管理")
    public ResultUtil<PageResult<ApiConfigDTO>> getApiConfigPage(@Valid ApiConfigQueryVO reqVO) {
        return ResultUtil.success(apiConfigService.getApiConfigPage(reqVO));
    }

}