package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Sets;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.app.service.ISystemRoleMenuService;
import com.wick.boot.module.system.mapper.ISystemRoleMenuMapper;
import com.wick.boot.module.system.model.dto.role.SystemRolePermsDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<SystemRolePermsDTO> rolePermsDTOS = this.systemRoleMenuMapper.selectRolePermsList(Sets.newHashSet());
        rolePermsToCache(rolePermsDTOS);
    }

    @Override
    public void refreshRolePermsCache(Set<String> codes) {
        /* Step-1: 清除所有key */
        Set<String> keys = codes.stream().map(GlobalCacheConstants::getRolePermsKey).collect(Collectors.toSet());
        redisService.deleteObject(keys);

        /* Step-2：通过 roleCode 获取角色-权限菜单 */
        List<SystemRolePermsDTO> rolePermsDTOS = this.systemRoleMenuMapper.selectRolePermsList(codes);
        rolePermsToCache(rolePermsDTOS);
    }

    /**
     * 将 Role - Perms 存入 Redis
     *
     * @param rolePermsDTOS 角色权限集合
     */
    private void rolePermsToCache(List<SystemRolePermsDTO> rolePermsDTOS) {
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRolePermsByRoleId(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        this.systemRoleMenuMapper.deleteRolePermsByRoleIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRolePermsByMenuId(List<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return;
        }
        this.systemRoleMenuMapper.deleteRolePermsByMenuIds(menuIds);
    }
}
