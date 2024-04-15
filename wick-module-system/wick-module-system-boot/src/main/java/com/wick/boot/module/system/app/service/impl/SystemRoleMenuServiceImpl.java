package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.app.service.ISystemRoleMenuService;
import com.wick.boot.module.system.mapper.ISystemRoleMenuMapper;
import com.wick.boot.module.system.model.dto.SystemRolePermsDTO;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
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
        Collection<String> keys = redisService.keys(GlobalCacheConstants.getRolePermsKey("*"));
        redisService.deleteObject(keys);

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
