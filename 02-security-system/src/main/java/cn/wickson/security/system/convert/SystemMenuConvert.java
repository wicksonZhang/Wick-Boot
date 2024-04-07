package cn.wickson.security.system.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.wickson.security.system.enums.MenuTypeEnum;
import cn.wickson.security.system.model.dto.SystemMenuDTO;
import cn.wickson.security.system.model.entity.SystemMenu;
import com.google.common.collect.Lists;
import org.mapstruct.*;
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
     * Convert entity to DTOs
     *
     * @param menuList menuList
     * @return List<SystemMenuDTO>
     */
    default List<SystemMenuDTO> entityToDTOS(List<SystemMenu> menuList) {
        List<SystemMenuDTO> dtoList = Lists.newArrayList();
        for (SystemMenu menu : menuList) {
            SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
            BeanUtil.copyProperties(menu, systemMenuDTO, "createBy", "updateBy", "deleted", "createTime", "updateTime");
            systemMenuDTO.setChildren(new ArrayList<>());
            systemMenuDTO.setRoles(new ArrayList<>());
            systemMenuDTO.setType(MenuTypeEnum.valueOf(menu.getType().getValue()));
            dtoList.add(systemMenuDTO);
        }
        return dtoList;
    }

}
