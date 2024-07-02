package com.wick.boot.module.system.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.module.system.enums.MenuTypeEnum;
import com.wick.boot.module.system.model.dto.menu.SystemMenuDTO;
import com.wick.boot.module.system.model.dto.menu.SystemMenuOptionsDTO;
import com.wick.boot.module.system.model.dto.menu.SystemRouteDTO;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.AddMenuReqVO;
import com.wick.boot.module.system.model.vo.menu.UpdateMenuReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;

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

    List<SystemRouteDTO> convertToRoute(List<SystemMenuDTO> rootMenus);

    default SystemRouteDTO menuToRoute(SystemMenuDTO menuDTO) {
        SystemRouteDTO routeVO = new SystemRouteDTO();

        // 设置 SystemRouteDTO 属性
        String routeName = StringUtils.capitalize(StrUtil.toCamelCase(menuDTO.getPath(), '-'));  // 路由 name 需要驼峰，首字母大写
        routeVO.setName(routeName); // 根据name路由跳转 this.$router.push({name:xxx})
        routeVO.setPath(menuDTO.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
        routeVO.setRedirect(menuDTO.getRedirect());
        routeVO.setComponent(menuDTO.getComponent());

        // 设置 SystemRouteDTO.Meta 属性
        SystemRouteDTO.Meta meta = new SystemRouteDTO.Meta();
        meta.setTitle(menuDTO.getName());
        meta.setIcon(menuDTO.getIcon());
        meta.setRoles(menuDTO.getRoles());
        meta.setHidden(CommonStatusEnum.DISABLE.getValue().equals(menuDTO.getVisible()));

        // 【菜单】是否开启页面缓存
        if (MenuTypeEnum.MENU.equals(menuDTO.getType()) && ObjectUtil.equals(menuDTO.getKeepAlive(), 1)) {
            meta.setKeepAlive(true);
        }
        // 【目录】只有一个子路由是否始终显示
        if (MenuTypeEnum.CATALOG.equals(menuDTO.getType()) && ObjectUtil.equals(menuDTO.getAlwaysShow(), 1)) {
            meta.setAlwaysShow(true);
        }
        routeVO.setMeta(meta);
        routeVO.setChildren(convertToRoute(menuDTO.getChildren()));
        return routeVO;
    }

}
