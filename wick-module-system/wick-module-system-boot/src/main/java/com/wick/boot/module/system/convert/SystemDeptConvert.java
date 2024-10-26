package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.dept.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.dept.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemDept;
import com.wick.boot.module.system.model.vo.dept.SystemDeptAddVO;
import com.wick.boot.module.system.model.vo.dept.SystemDeptUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理 - Dept - Convert
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Mapper
public interface SystemDeptConvert {

    SystemDeptConvert INSTANCE = Mappers.getMapper(SystemDeptConvert.class);

    /**
     * 将部门新增请求参数VO转换为SystemDept实体
     *
     * @param reqVO 部门新增请求参数VO
     * @return 转换后的SystemDept实体
     */
    SystemDept convertAddVoToEntity(SystemDeptAddVO reqVO);

    /**
     * 将部门更新请求参数VO转换为SystemDept实体
     *
     * @param reqVO 部门更新请求参数VO
     * @return 转换后的SystemDept实体
     */
    SystemDept convertUpdateVoToEntity(SystemDeptUpdateVO reqVO);

    /**
     * 将SystemDept实体转换为SystemDeptDTO
     * 忽略'children'字段的转换
     *
     * @param dept 部门实体
     * @return 转换后的SystemDeptDTO
     */
    @Mapping(target = "children", ignore = true)
    SystemDeptDTO convertEntityToDTO(SystemDept dept);

    /**
     * 将SystemDept实体转换为SystemDeptDTO，并初始化'children'为空列表
     *
     * @param dept 部门实体
     * @return 转换后的SystemDeptDTO，且'children'属性初始化为空列表
     */
    default SystemDeptDTO convertEntityToDtoWithChildren(SystemDept dept) {
        SystemDeptDTO dto = convertEntityToDTO(dept);
        // 初始化 'children' 为空列表
        dto.setChildren(new ArrayList<>());
        return dto;
    }

    /**
     * 将SystemDeptDTO转换为SystemDeptOptionsDTO
     * 将'id'映射为'value'，'name'映射为'label'
     *
     * @param systemDeptDTO 系统部门DTO
     * @return 转换后的SystemDeptOptionsDTO
     */
    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    SystemDeptOptionsDTO convertToOptionsDTO(SystemDeptDTO systemDeptDTO);

    /**
     * 将SystemDeptDTO列表转换为SystemDeptOptionsDTO列表
     *
     * @param deptDTOList 部门DTO列表
     * @return 转换后的SystemDeptOptionsDTO列表
     */
    List<SystemDeptOptionsDTO> convertDtoListToOptionsList(List<SystemDeptDTO> deptDTOList);

    /**
     * 将SystemDept实体列表转换为SystemDeptOptionsDTO列表
     *
     * @param systemDeptList 部门实体列表
     * @return 转换后的SystemDeptOptionsDTO列表
     */
    List<SystemDeptOptionsDTO> convertEntitiesToOptionsList(List<SystemDept> systemDeptList);

    /**
     * 将单个SystemDept实体转换为SystemDeptOptionsDTO
     * 将'id'映射为'value'，'name'映射为'label'
     *
     * @param systemDept 系统部门实体
     * @return 转换后的SystemDeptOptionsDTO
     */
    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    SystemDeptOptionsDTO convertEntityToOptions(SystemDept systemDept);
}
