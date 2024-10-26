package com.wick.boot.module.system.service;

import com.wick.boot.module.system.model.dto.dept.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.dept.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.vo.dept.SystemDeptAddVO;
import com.wick.boot.module.system.model.vo.dept.SystemDeptQueryVO;
import com.wick.boot.module.system.model.vo.dept.SystemDeptUpdateVO;

import java.util.List;

/**
 * 部门管理-服务层
 *
 * @author Lenovo
 */
public interface SystemDeptService {

    /**
     * 新增部门
     *
     * @param reqVO 新增请求参数
     * @return
     */
    Long addSystemDept(SystemDeptAddVO reqVO);

    /**
     * 更新部门
     *
     * @param reqVO 更新请求参数
     */
    void updateSystemDept(SystemDeptUpdateVO reqVO);

    /**
     * 删除部门信息
     *
     * @param ids 部门主键Ids
     */
    void deleteSystemDept(List<Long> ids);

    /**
     * 通过ID获取部门信息
     *
     * @param id 部门ID
     * @return SystemDeptDTO 部门DTO
     */
    SystemDeptDTO getSystemDept(Long id);

    /**
     * 查询部门列表信息
     *
     * @param reqVO 查询信息
     * @return List<SystemDeptDTO>
     */
    List<SystemDeptDTO> getSystemDeptList(SystemDeptQueryVO reqVO);

    /**
     * 获取部门下拉选项
     *
     * @return List<DeptOptionsDTO>
     */
    List<SystemDeptOptionsDTO> getSystemDeptOptionsList();

    /**
     * 获取部门信息列表
     *
     * @return List<SystemDeptOptionsDTO>
     */
    List<SystemDeptOptionsDTO> getSystemDeptOptionsListAll();

}
