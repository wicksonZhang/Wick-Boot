package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.SystemRoleDTO;
import com.wick.boot.module.system.model.entity.SystemRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 后台管理 - Role - Convert
 *
 * @author ZhangZiHeng
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
}
