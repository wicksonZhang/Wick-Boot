package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.SystemUserDTO;
import com.wick.boot.module.system.model.dto.SystemUserInfoDTO;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.vo.user.AddUserVO;
import com.wick.boot.module.system.model.vo.user.UpdateUserVO;
import com.wick.boot.module.system.model.vo.user.UserImportVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 后台管理 - 用户信息
 *
 * @author ZhangZiHeng
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
     * Convert entity To SystemUserInfoDTO
     *
     * @param systemUser 用户信息
     * @return SystemUserInfoDTO
     */
    @Mapping(target = "userId", source = "id")
    SystemUserInfoDTO entityToDTO1(SystemUser systemUser);

    /**
     * Convert addVO To SystemUser
     *
     * @param reqVO 新增请求参数
     * @return SystemUser
     */
    SystemUser addVoToEntity(AddUserVO reqVO);

    /**
     * Convert updateVO To SystemUser
     *
     * @param reqVO 更新请求参数
     * @return SystemUser
     */
    SystemUser updateVoToEntity(UpdateUserVO reqVO);


    SystemUser importVo2Entity(UserImportVO userImportVO);

    List<SystemUserDTO> entityToDTOList(List<SystemUser> systemUsers);
}
