package cn.wickson.security.system.app.service;

import cn.wickson.security.system.model.dto.SystemDeptDTO;
import cn.wickson.security.system.model.vo.QueryDeptListReqVO;

import java.util.List;

/**
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

}
