package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.SystemRoleDTO;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.vo.role.AddRoleVo;
import com.wick.boot.module.system.model.vo.role.UpdateRoleVo;
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
    SystemRole addVoToEntity(AddRoleVo reqVO);

    /**
     * Convert updateVO To Entity
     *
     * @param reqVO 更新请求参数
     * @return 角色信息
     */
    SystemRole updateVoToEntity(UpdateRoleVo reqVO);
}
