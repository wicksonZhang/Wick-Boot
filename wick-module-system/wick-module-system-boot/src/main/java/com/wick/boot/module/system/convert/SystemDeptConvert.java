package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemDept;
import com.wick.boot.module.system.model.vo.dept.AddDeptReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理 - Dept - Convert
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Mapper
public interface SystemDeptConvert {

    SystemDeptConvert INSTANCE = Mappers.getMapper(SystemDeptConvert.class);

    /**
     * Convert addVo To Entity
     *
     * @param reqVO 部门请求参数VO
     * @return SystemDept 系统部门
     */
    SystemDept addVoToEntity(AddDeptReqVO reqVO);

    /**
     * Convert entity to DTO
     *
     * @param dept Department entity
     * @return SystemDeptDTO
     */
    @Mapping(target = "children", ignore = true)
    SystemDeptDTO entityToDTO(SystemDept dept);

    default SystemDeptDTO entityToDTOWithChildren(SystemDept dept) {
        SystemDeptDTO dto = entityToDTO(dept);
        dto.setChildren(new ArrayList<>()); // 初始化 children 属性为空列表
        return dto;
    }

    /**
     * Convert SystemDeptDTO To SystemDeptOptionsDTO
     *
     * @param systemDeptDTO systemDeptDTO
     * @return SystemDeptOptionsDTO
     */
    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    SystemDeptOptionsDTO dtoToDTO(SystemDeptDTO systemDeptDTO);

    /**
     * Convert entity To DTOList
     *
     * @param deptDTOList 部门DTO集合
     * @return
     */
    List<SystemDeptOptionsDTO> entityToDTOList(List<SystemDeptDTO> deptDTOList);

}
