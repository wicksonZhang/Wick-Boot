package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.user.SystemUserDTO;
import com.wick.boot.module.system.model.dto.user.SystemUserLoginInfoDTO;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.vo.user.SystemUserAddVO;
import com.wick.boot.module.system.model.vo.user.SystemUserImportVO;
import com.wick.boot.module.system.model.vo.user.SystemUserUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 后台管理 - 用户信息
 *
 * @author Wickson
 * @date 2024-04-02
 */
@Mapper(componentModel = "spring")
public interface SystemUserConvert {

    SystemUserConvert INSTANCE = Mappers.getMapper(SystemUserConvert.class);

    /**
     * Convert entity To SystemUserDTO
     *
     * @param systemUser 用户信息
     * @return SystemUserDTO
     */
    SystemUserDTO entityToDTO(SystemUser systemUser);

    /**
     * Convert entity To SystemUserLoginInfoDTO
     *
     * @param systemUser 用户信息
     * @return SystemUserLoginInfoDTO
     */
    @Mapping(target = "userId", source = "id")
    SystemUserLoginInfoDTO entityToDTO1(SystemUser systemUser);

    /**
     * Convert addVO To SystemUser
     *
     * @param reqVO 新增请求参数
     * @return SystemUser
     */
    SystemUser addVoToEntity(SystemUserAddVO reqVO);

    /**
     * Convert updateVO To SystemUser
     *
     * @param reqVO 更新请求参数
     * @return SystemUser
     */
    SystemUser updateVoToEntity(SystemUserUpdateVO reqVO);


    SystemUser importVo2Entity(SystemUserImportVO systemUserImportVO);

    List<SystemUserDTO> entityToDTOList(List<SystemUser> systemUsers);
}
