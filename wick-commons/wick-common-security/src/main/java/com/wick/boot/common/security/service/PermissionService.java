package com.wick.boot.common.security.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Security 权限校验
 */
@Slf4j
@Component("ss")
public class PermissionService {

    @Resource
    private RedisService redisService;

    /**
     * 判断当前登录用户是否拥有操作权限
     *
     * @param requiredPerm 所需权限
     * @return 是否有权限
     */
    public boolean hasPerm(String requiredPerm) {
        if (StrUtil.isBlank(requiredPerm)) {
            return false;
        }

        /* Step-1： 如果是超级管理员，则进行放行 */
        if (GlobalConstants.ROOT_ROLE_CODE.equals(requiredPerm)) {
            return true;
        }

        /* Step-2: 检验当前角色信息是否拥有权限 */
        Set<String> roleCodes = SecurityUtils.getRoles();
        if (CollUtil.isEmpty(roleCodes)) {
            return false;
        }
        // 判断当前 requiredPerm 是否在 permissions 中
        Set<String> permissions = getPermissions(roleCodes);
        return CollUtil.contains(permissions, requiredPerm);
    }

    /**
     * 获取当前用户的所有角色权限Code
     *
     * @param roleCodes 角色信息
     * @return 权限编码
     */
    private Set<String> getPermissions(Set<String> roleCodes) {
        Set<String> resultSet = Sets.newHashSet();
        for (String roleCode : roleCodes) {
            String rolePermsKey = GlobalCacheConstants.getRolePermsKey(roleCode);
            Set<String> permissionCodes = this.redisService.getCacheSet(rolePermsKey);
            resultSet.addAll(permissionCodes);
        }
        return resultSet;
    }

}
