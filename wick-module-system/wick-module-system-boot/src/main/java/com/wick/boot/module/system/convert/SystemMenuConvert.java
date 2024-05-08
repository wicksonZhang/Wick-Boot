package com.wick.boot.module.system.convert;

import cn.hutool.core.bean.BeanUtil;
import com.wick.boot.module.system.enums.MenuTypeEnum;
import com.wick.boot.module.system.model.dto.SystemMenuDTO;
import com.wick.boot.module.system.model.dto.SystemMenuOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.AddMenuReqVO;
import com.wick.boot.module.system.model.vo.menu.UpdateMenuReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理 - 菜单 Convert
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Mapper
public interface SystemMenuConvert {

    SystemMenuConvert INSTANCE = Mappers.getMapper(SystemMenuConvert.class);

    /**
     * Convert entity to DTO
     *
     * @return SystemMenuDTO
     */
    default SystemMenuDTO entityToDTOWithChildren(SystemMenu systemMenu) {
        SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
        BeanUtil.copyProperties(systemMenu, systemMenuDTO, "createBy", "updateBy", "deleted", "createTime", "updateTime");
        systemMenuDTO.setChildren(new ArrayList<>());
        systemMenuDTO.setRoles(new ArrayList<>());
        systemMenuDTO.setType(MenuTypeEnum.valueOf(systemMenu.getType().getValue()));
        return systemMenuDTO;
    }

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数
     * @return SystemMenu 系统菜单
     */
    SystemMenu addVoToEntity(AddMenuReqVO reqVO);

    /**
     * Convert updateVo To entity
     *
     * @param reqVO 更新请求参数
     * @return SystemMenu 系统菜单
     */
    SystemMenu updateVoToEntity(UpdateMenuReqVO reqVO);

    /**
     * Convert SystemMenuDTO To SystemMenuOptionsDTO
     *
     * @param systemMenuDTO systemMenuDTO
     * @return SystemMenuOptionsDTO
     */
    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    SystemMenuOptionsDTO dtoToDTO(SystemMenuDTO systemMenuDTO);

    /**
     * Covert entity To DTOList
     *
     * @param menuDTOList 菜单DTO集合
     * @return
     */
    List<SystemMenuOptionsDTO> entityToDTOList(List<SystemMenuDTO> menuDTOList);
}
