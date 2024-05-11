package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.common.security.util.SecurityUtils;
import com.wick.boot.module.system.app.service.AbstractSystemUserAppService;
import com.wick.boot.module.system.app.service.ISystemUserService;
import com.wick.boot.module.system.convert.SystemUserConvert;
import com.wick.boot.module.system.mapper.ISystemUserMapper;
import com.wick.boot.module.system.mapper.ISystemUserRoleMapper;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import com.wick.boot.module.system.model.dto.SystemUserDTO;
import com.wick.boot.module.system.model.dto.SystemUserInfoDTO;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.entity.SystemUserRole;
import com.wick.boot.module.system.model.vo.user.AddUserVO;
import com.wick.boot.module.system.model.vo.user.QueryUserPageReqVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理-服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Service
public class SystemUserServiceImpl extends AbstractSystemUserAppService implements ISystemUserService {

    @Resource
    private ISystemUserMapper userMapper;

    @Resource
    private ISystemUserRoleMapper userRoleMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public SystemUserDTO getUserInfo(String username) {
        SystemUser systemUser = userMapper.selectByUsername(username);
        return SystemUserConvert.INSTANCE.entityToDTO(systemUser);
    }

    @Override
    public SystemUserInfoDTO getCurrentUserInfo() {
        /* Step-1: 获取当前登录用户信息 */
        LoginUserInfoDTO userDetails = SecurityUtils.getUserDetails();
        if (ObjUtil.isNull(userDetails)) {
            return SystemUserInfoDTO.builder().build();
        }

        /* Step-2: 通过用户名称获取用户信息 */
        SystemUser systemUser = this.userMapper.selectByUsername(userDetails.getUsername());
        // 封装用户信息
        SystemUserInfoDTO userInfoDTO = SystemUserConvert.INSTANCE.entityToDTO1(systemUser);

        // 封装角色信息
        userInfoDTO.setRoles(userDetails.getRoles());

        /* Step-4: 获取权限信息 */
        userInfoDTO.setPerms(getPerms(userDetails.getRoles()));
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
    @Transactional(rollbackFor = Exception.class)
    public void addUser(AddUserVO reqVO) {
        /* Step-1: 验证新增参数信息 */
        this.validateAddParams(reqVO);

        /* Step-2：新增用户信息 */
        SystemUser systemUser = SystemUserConvert.INSTANCE.addVoToEntity(reqVO);
        String password = passwordEncoder.encode(GlobalConstants.DEFAULT_USER_PASSWORD);
        systemUser.setPassword(password);
        this.userMapper.insert(systemUser);

        /* Step-3: 新增用户-角色信息 */
        Long userId = systemUser.getId();
        List<SystemUserRole> userRoleList = reqVO.getRoleIds().stream()
                .map(roleId -> new SystemUserRole(userId, roleId))
                .collect(Collectors.toList());
        this.userRoleMapper.insertBatch(userRoleList);
    }

}
