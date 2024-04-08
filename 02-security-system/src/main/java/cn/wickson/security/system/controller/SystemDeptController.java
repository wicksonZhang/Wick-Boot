package cn.wickson.security.system.controller;

import cn.wickson.security.commons.result.ResultUtil;
import cn.wickson.security.system.app.service.ISystemDeptService;
import cn.wickson.security.system.model.dto.SystemDeptDTO;
import cn.wickson.security.system.model.dto.SystemDeptOptionsDTO;
import cn.wickson.security.system.model.vo.QueryDeptListReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 后台管理 - 部门信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-03
 */
@RestController
@RequestMapping("/dept")
@Api(tags = "系统管理 - 部门信息")
public class SystemDeptController {

    @Resource
    private ISystemDeptService systemDeptService;

    @ApiOperation(value = "获取部门列表", notes = "系统管理 - 部门信息")
    @GetMapping
    public ResultUtil<List<SystemDeptDTO>> listDepartments(@Valid QueryDeptListReqVO reqVO) {
        return ResultUtil.success(systemDeptService.listDepartments(reqVO));
    }

    @ApiOperation(value = "获取部门下拉选项", notes = "系统管理 - 部门信息")
    @GetMapping("/options")
    public ResultUtil<List<SystemDeptOptionsDTO>> listDeptOptions() {
        return ResultUtil.success(systemDeptService.listDeptOptions());
    }
}
