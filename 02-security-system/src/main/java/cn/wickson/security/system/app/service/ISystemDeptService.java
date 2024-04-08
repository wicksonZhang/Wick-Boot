package cn.wickson.security.system.app.service;

import cn.wickson.security.system.model.dto.SystemDeptDTO;
import cn.wickson.security.system.model.dto.SystemDeptOptionsDTO;
import cn.wickson.security.system.model.vo.QueryDeptListReqVO;

import java.util.List;

/**
 * 部门管理-服务层
 *
 * @author Lenovo
 */
public interface ISystemDeptService {

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
