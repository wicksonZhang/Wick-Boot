package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.role.SystemRoleDTO;
import com.wick.boot.module.system.model.dto.role.SystemRoleOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.vo.role.SystemRoleAddVO;
import com.wick.boot.module.system.model.vo.role.SystemRoleUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 后台管理 - Role - Convert
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Mapper
public interface SystemRoleConvert {

    SystemRoleConvert INSTANCE = Mappers.getMapper(SystemRoleConvert.class);

    /**
     * Convert entity to DTOs
     *
     * @param systemRoles List of systemRoles entities
     * @return List<SystemDeptDTO>
     */
    List<SystemRoleDTO> entityToDTOS(List<SystemRole> systemRoles);

    /**
     * Convert entity To DTO
     *
     * @param systemRoles 系统角色
     * @return SystemRoleDTO
     */
    SystemRoleDTO entityToDTO(SystemRole systemRoles);

    /**
     * Convert addVO To Entity
     *
     * @param reqVO 新增请求参数
     * @return 角色信息
     */
    SystemRole addVoToEntity(SystemRoleAddVO reqVO);

    /**
     * Convert updateVO To Entity
     *
     * @param reqVO 更新请求参数
     * @return 角色信息
     */
    SystemRole updateVoToEntity(SystemRoleUpdateVO reqVO);

    /**
     * Convert entity To Options
     *
     * @param roles 角色集合
     * @return List<SystemRoleOptionsDTO>
     */
    List<SystemRoleOptionsDTO> entityToOptions(List<SystemRole> roles);

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    SystemRoleOptionsDTO entityToOption(SystemRole role);
}
