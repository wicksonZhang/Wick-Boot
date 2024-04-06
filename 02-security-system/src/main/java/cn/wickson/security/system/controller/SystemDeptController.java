package cn.wickson.security.system.controller;

import cn.wickson.security.commons.result.ResultUtil;
import cn.wickson.security.system.app.service.ISystemDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ZhangZiHeng
 * @date 2024-04-03
 */
@RestController
@RequestMapping("/dept")
@Api(tags = "系统管理 - 部门信息")
public class SystemDeptController {

    @Resource
    private ISystemDeptService systemDeptService;

    @ApiOperation(value = "获取部门列表")
    @GetMapping
    public ResultUtil<List<DeptVO>> listDepartments(DeptQuery queryParams) {
    }


}
