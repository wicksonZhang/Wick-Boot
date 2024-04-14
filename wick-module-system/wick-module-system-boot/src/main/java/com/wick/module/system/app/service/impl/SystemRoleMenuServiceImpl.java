package com.wick.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wick.module.system.mapper.ISystemRoleMenuMapper;
import com.wick.module.system.model.dto.SystemRolePermsDTO;
import com.wick.module.system.app.service.ISystemRoleMenuService;
import com.wick.common.core.constant.GlobalCacheConstants;
import com.wick.common.redis.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class SystemRoleMenuServiceImpl implements ISystemRoleMenuService {

    @Resource
    private ISystemRoleMenuMapper systemRoleMenuMapper;

    @Resource
    private RedisService redisService;

    @PostConstruct
    public void loadRolePermsCache() {
        refreshRolePermsCache();
    }

    @Override
    public void refreshRolePermsCache() {
        /* Step-1: 清除所有key */
        String rolePermsKey = GlobalCacheConstants.getRolePermsKey("*");
        redisService.deleteObject(rolePermsKey);

        /* Step-2：通过 roleCode 获取角色-权限菜单 */
        List<SystemRolePermsDTO> rolePermsDTOS = this.systemRoleMenuMapper.selectRolePermsList(null);
        if (CollUtil.isEmpty(rolePermsDTOS)) {
            return;
        }
        /* Step-3: 将 角色-权限 存入redis */
        rolePermsDTOS.forEach(rolePerms -> {
            String roleCode = rolePerms.getRoleCode();
            Set<String> perms = rolePerms.getPerms();
            String key = GlobalCacheConstants.getRolePermsKey(roleCode);
            redisService.setCacheSet(key, perms);
        });
    }


}
