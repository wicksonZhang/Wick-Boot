package com.wick.boot.module.system.app.service;

import com.wick.boot.module.system.model.dto.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.vo.dept.AddDeptReqVO;
import com.wick.boot.module.system.model.vo.dept.QueryDeptListReqVO;

import java.util.List;

/**
 * 部门管理-服务层
 *
 * @author Lenovo
 */
public interface ISystemDeptService {

    /**
     * 新增部门
     *
     * @param reqVO 新增请求参数
     * @return Long 主键ID
     */
    Long addDepartment(AddDeptReqVO reqVO);

    /**
     * 查询部门列表信息
     *
     * @param reqVO 查询信息
     * @return List<SystemDeptDTO>
     */
    List<SystemDeptDTO> listDepartments(QueryDeptListReqVO reqVO);

    /**
     * 获取部门下拉选项
     *
     * @return List<DeptOptionsDTO>
     */
    List<SystemDeptOptionsDTO> listDeptOptions();

}
