package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wickson.security.commons.constant.SystemConstants;
import cn.wickson.security.system.app.service.ISystemMenuService;
import cn.wickson.security.system.convert.SystemMenuConvert;
import cn.wickson.security.system.mapper.ISystemMenuMapper;
import cn.wickson.security.system.model.dto.SystemMenuDTO;
import cn.wickson.security.system.model.dto.SystemMenuRouteDTO;
import cn.wickson.security.system.model.entity.SystemMenu;
import cn.wickson.security.system.model.vo.QueryMenuListReqVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 后台管理 - 菜单信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<ISystemMenuMapper, SystemMenu> implements ISystemMenuService {

    @Resource
    private ISystemMenuMapper systemMenuMapper;

    @Override
    public List<SystemMenuDTO> listMenus(QueryMenuListReqVO queryParams) {
        /* Step-1: 获取菜单信息 */
        List<SystemMenu> menuList = systemMenuMapper.selectList(queryParams);
        if (CollUtil.isEmpty(menuList)) {
            return Lists.newArrayList();
        }

        /* Step-2: 使用Map存储转换后的DTO对象, 并将其存入Map */
        List<SystemMenuDTO> menuDTOS = SystemMenuConvert.INSTANCE.entityToDTOS(menuList);
        Map<Long, SystemMenuDTO> menuMap = menuDTOS.stream().collect(Collectors.toMap(SystemMenuDTO::getId, dto -> dto));

        /* Step-3: 构建菜单树 */
        Long rootNodeId = SystemConstants.ROOT_NODE_ID;
        menuDTOS.stream().filter(menu -> !Objects.equals(rootNodeId, menu.getParentId())).forEach(menu -> {
            SystemMenuDTO systemMenuDTO = menuMap.get(menu.getParentId());
            if (systemMenuDTO != null) {
                systemMenuDTO.getChildren().add(menu);
            }
        });

        /* Step-4: 返回结果集 */
        return menuMap.values()
                .stream()
                .filter(deptDTO -> Objects.equals(rootNodeId, deptDTO.getParentId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SystemMenuRouteDTO> listRoutes() {
        return null;
    }
}
