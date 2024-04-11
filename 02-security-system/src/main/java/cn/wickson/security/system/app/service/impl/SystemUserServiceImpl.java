package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.wickson.security.commons.constant.GlobalCacheConstants;
import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.app.service.ISystemRoleMenuService;
import cn.wickson.security.system.app.service.ISystemUserService;
import cn.wickson.security.system.convert.SystemUserConvert;
import cn.wickson.security.system.mapper.ISystemRoleMenuMapper;
import cn.wickson.security.system.mapper.ISystemUserMapper;
import cn.wickson.security.system.model.dto.AuthUserInfoDTO;
import cn.wickson.security.system.model.dto.SystemUserDTO;
import cn.wickson.security.system.model.dto.SystemUserInfoDTO;
import cn.wickson.security.system.model.entity.SystemUser;
import cn.wickson.security.system.model.vo.QueryUserPageReqVO;
import cn.wickson.security.system.plugin.redis.RedisService;
import cn.wickson.security.system.security.model.SystemUserDetails;
import cn.wickson.security.system.security.service.SecurityUserDetails;
import cn.wickson.security.system.security.util.SecurityUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户管理-服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Service
public class SystemUserServiceImpl implements ISystemUserService {

    @Resource
    private ISystemUserMapper userMapper;

    @Resource
    private RedisService redisService;


    @Override
    public SystemUserDTO getUserInfo(String username) {
        SystemUser systemUser = userMapper.selectByUsername(username);
        return SystemUserConvert.INSTANCE.entityToDTO(systemUser);
    }

    @Override
    public PageResult<SystemUserDTO> getUserPage(QueryUserPageReqVO reqVO) {
        Page<SystemUserDTO> pageResult = userMapper.selectPage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO
        );
        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return PageResult.empty();
        }
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal());
    }

    @Override
    public AuthUserInfoDTO getUserByName(String username) {
        /* Step-1: 通过用户名获取用户信息： userId, username, nickname, password, status, dept_id , code */
        AuthUserInfoDTO userInfoDTO = userMapper.selectAuthUserInfo(username);
        if (ObjUtil.isNull(userInfoDTO)) {
            return null;
        }

        /* Step-2: 通过角色信息获取权限信息 */
        Set<String> roles = userInfoDTO.getRoles();
        userInfoDTO.setPerms(getPerms(roles));
        return userInfoDTO;
    }

    @Override
    public SystemUserInfoDTO getCurrentUserInfo() {
        /* Step-1: 获取当前登录用户信息 */
        SystemUserDetails userDetails = SecurityUtils.getUserDetails();
        if (ObjUtil.isNull(userDetails)) {
            return SystemUserInfoDTO.builder().build();
        }

        /* Step-2: 通过用户名称获取用户信息 */
        SystemUser systemUser = this.userMapper.selectByUsername(userDetails.getUsername());
        // 封装用户信息
        SystemUserInfoDTO userInfoDTO = SystemUserConvert.INSTANCE.entityToDTO1(systemUser);

        /* Step-3: 获取角色信息 */
        Set<String> roles = SecurityUtils.getRoles();
        // 封装角色信息
        userInfoDTO.setRoles(roles);

        /* Step-4: 获取权限信息 */
        Set<String> perms = getPerms(roles);
        userInfoDTO.setPerms(perms);
        return userInfoDTO;
    }

    /**
     * 通过角色Code获取权限菜单
     *
     * @param roles 角色Code
     * @return Set<String>
     */
    private Set<String> getPerms(Set<String> roles) {
        Set<String> perms = Sets.newHashSet();
        for (String roleCode : roles) {
            String key = GlobalCacheConstants.getRolePermsKey(roleCode);
            perms.addAll(redisService.getCacheSet(key));
        }
        return perms;
    }

}
