package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.common.security.util.SecurityUtils;
import com.wick.boot.module.system.app.service.AbstractSystemUserAppService;
import com.wick.boot.module.system.app.service.ISystemUserService;
import com.wick.boot.module.system.convert.SystemUserConvert;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.listener.user.UserImportListener;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import com.wick.boot.module.system.model.dto.SystemUserDTO;
import com.wick.boot.module.system.model.dto.SystemUserInfoDTO;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.entity.SystemUserRole;
import com.wick.boot.module.system.model.vo.user.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;
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
        SystemUser systemUser = userMapper.selectByUsername(userDetails.getUsername());
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
        assignUserRole(systemUser.getId(), reqVO.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UpdateUserVO reqVO) {
        /* Step-1: 验证更新用户信息是否正确 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 更新用户信息  */
        SystemUser systemUser = SystemUserConvert.INSTANCE.updateVoToEntity(reqVO);
        this.userMapper.updateById(systemUser);

        /* Step-3: 更新用户-角色信息 */
        assignUserRole(systemUser.getId(), reqVO.getRoleIds());
    }

    /**
     * 分配用户-角色信息
     *
     * @param userId    用户Id
     * @param targetIds 角色Id集合
     */
    private void assignUserRole(Long userId, Set<Long> targetIds) {
        // 注意：这里做新增和保存可能存在的问题，之前已经存在了用户-角色信息，之前不存在用户-角色信息，之前存在现在有需要删除的用户-角色信息。
        // case1：保存之前没有的 用户-角色 信息
        // case2：删除之前有的 用户-角色 信息

        // 查询用户已有的角色信息
        List<SystemUserRole> userRoles = this.userRoleMapper.selectListByUserId(userId);
        userRoles = CollUtil.emptyIfNull(userRoles);

        // 提取用户已有的角色 ID
        Set<Long> sourceIds = userRoles.stream().map(SystemUserRole::getRoleId).collect(Collectors.toSet());
        // 保存之前没有的 用户-角色 信息
        Collection<Long> saveIds = CollectionUtil.subtract(targetIds, sourceIds);
        if (CollUtil.isNotEmpty(saveIds)) {
            List<SystemUserRole> saveUserRoleList = saveIds.stream()
                    .map(roleId -> new SystemUserRole(userId, roleId))
                    .collect(Collectors.toList());
            this.userRoleMapper.insertBatch(saveUserRoleList);
        }

        // 删除之前有的 用户-角色 信息
        Collection<Long> deleteIds = CollectionUtil.subtract(sourceIds, targetIds);
        if (CollUtil.isNotEmpty(deleteIds)) {
            Set<Long> roleIds = Sets.newHashSet(deleteIds);
            this.userRoleMapper.deleteBatchByUserIdAndRoleIds(userId, roleIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(List<Long> ids) {
        /* Step-1: 校验删除用户参数 */
        List<SystemUser> systemUsers = this.userMapper.selectBatchIds(ids);
        this.validateDeleteParams(systemUsers, ids);

        /* Step-2: 删除用户信息 */
        this.userMapper.deleteBatchIds(systemUsers);

        /* Step-3: 删除用户-角色信息 */
        this.userRoleMapper.deleteBatchByUserIds(ids);
    }

    @Override
    public SystemUserDTO getUserById(Long id) {
        /* Step-1: 通过用户id获取角色信息 */
        SystemUser systemUser = userMapper.selectById(id);
        SystemUserDTO systemUserDTO = SystemUserConvert.INSTANCE.entityToDTO(systemUser);

        /* Step-2: 通过用户id获取角色id集合 */
        List<SystemUserRole> userRoles = this.userRoleMapper.selectListByUserId(systemUser.getId());
        List<Long> roleIds = userRoles.stream().map(SystemUserRole::getRoleId).collect(Collectors.toList());
        systemUserDTO.setRoleIds(roleIds);
        return systemUserDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPwd(UpdateUserPwdVO reqVO) {
        /* Step-1: 验证更新参数是否正确 */
        SystemUser systemUser = this.userMapper.selectById(reqVO.getUserId());
        this.validateUpdateByPwdParams(systemUser);

        /* Step-2: 更新用户密码信息 */
        String password = passwordEncoder.encode(reqVO.getPassword());
        systemUser.setPassword(password);
        this.userMapper.updateById(systemUser);
    }

    @Override
    public void downloadTemplate(HttpServletResponse response) {
        String fileName = "用户导入模板.xlsx";
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            String fileClassPath = "excel-templates" + File.separator + fileName;
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileClassPath);
            ServletOutputStream outputStream = response.getOutputStream();
            ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            excelWriter.finish();
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.USER_DOWNLOAD_ERROR);
        }
    }

    @Override
    public void exportUsers(QueryUserPageReqVO queryParams, HttpServletResponse response) {
        String fileName = "用户列表.xlsx";
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            List<UserExportVO> exportUserList = this.userMapper.listExportUsers(queryParams);
            EasyExcel.write(response.getOutputStream(), UserExportVO.class).sheet("用户列表").doWrite(exportUserList);
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.USER_EXPORT_ERROR);
        }
    }

    @Override
    public void importUsers(Long deptId, MultipartFile file) {
        UserImportListener listener = new UserImportListener(deptId);
        try {
            // TODO 后续会通过邮件形式通知
            EasyExcel.read(file.getInputStream(), UserImportVO.class, listener);
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.USER_IMPORT_ERROR);
        }
    }

}
