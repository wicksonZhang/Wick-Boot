package cn.wickson.security.system.controller;

import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.commons.result.ResultUtil;
import cn.wickson.security.system.app.service.ISystemDictDataService;
import cn.wickson.security.system.app.service.ISystemDictTypeService;
import cn.wickson.security.system.model.dto.SystemDictDataDTO;
import cn.wickson.security.system.model.dto.SystemDictTypeDTO;
import cn.wickson.security.system.model.vo.QueryDictDataPageReqVO;
import cn.wickson.security.system.model.vo.QueryDictTypePageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 后台管理 - 字典类型管理
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "系统管理 - 字典信息")
public class SystemDictController {

    @Resource
    private ISystemDictTypeService dictTypeService;

    @Resource
    private ISystemDictDataService dictDataService;

    @GetMapping("/types/page")
    @ApiOperation(value = "获取字典类型分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictTypeDTO>> getDictTypePage(@Valid QueryDictTypePageReqVO reqVO) {
        return ResultUtil.success(dictTypeService.getDictTypePage(reqVO));
    }

    @GetMapping("/data/page")
    @ApiOperation(value = "获取字典数据分页", notes = "字典信息")
    public ResultUtil<PageResult<SystemDictDataDTO>> getDictDataPage(@Valid QueryDictDataPageReqVO reqVO) {
        return ResultUtil.success(dictDataService.getDictDataPage(reqVO));
    }

}
