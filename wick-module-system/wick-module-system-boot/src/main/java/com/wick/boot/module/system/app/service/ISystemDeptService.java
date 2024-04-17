package com.wick.boot.module.system.app.service;

import com.wick.boot.module.system.model.dto.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.vo.dept.AddDeptReqVO;
import com.wick.boot.module.system.model.vo.dept.QueryDeptListReqVO;
import com.wick.boot.module.system.model.vo.dept.UpdateDeptReqVO;

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
     */
    void addDepartment(AddDeptReqVO reqVO);

    /**
     * 更新部门
     *
     * @param reqVO 更新请求参数
     */
    void updateDepartment(UpdateDeptReqVO reqVO);

    /**
     * 删除部门信息
     *
     * @param ids 部门主键Ids
     */
    void deleteDept(List<Long> ids);

    /**
     * 通过ID获取部门信息
     *
     * @param id 部门ID
     * @return SystemDeptDTO 部门DTO
     */
    SystemDeptDTO getDepartmentById(Long id);

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
