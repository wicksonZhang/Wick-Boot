package cn.wickson.security.system.convert;

import cn.wickson.security.system.enums.MenuTypeEnum;
import cn.wickson.security.system.model.dto.SystemMenuDTO;
import cn.wickson.security.system.model.entity.SystemMenu;
import com.google.common.collect.Lists;
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
     * @param systemMenu SystemMenu entity
     * @return SystemMenuDTO
     */
    @Mappings({
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "type", ignore = true)
    })
    SystemMenuDTO entityToDTO(SystemMenu systemMenu);

    /**
     * Convert entity to DTOs
     *
     * @param menuList menuList
     * @return List<SystemMenuDTO>
     */
    default List<SystemMenuDTO> entityToDTOS(List<SystemMenu> menuList) {
        List<SystemMenuDTO> dtoList = Lists.newArrayList();
        for (SystemMenu menu : menuList) {
            SystemMenuDTO systemMenuDTO = entityToDTO(menu);
            systemMenuDTO.setChildren(new ArrayList<>());
            systemMenuDTO.setType(MenuTypeEnum.valueOf(menu.getType().getValue()));
            dtoList.add(systemMenuDTO);
        }
        return dtoList;
    }

}
