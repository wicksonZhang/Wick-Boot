package com.wick.boot.module.system.service.impl;

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
import com.wick.boot.module.system.convert.SystemUserConvert;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.enums.user.UserImportListener;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import com.wick.boot.module.system.model.dto.user.SystemUserDTO;
import com.wick.boot.module.system.model.dto.user.SystemUserLoginInfoDTO;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.entity.SystemUserRole;
import com.wick.boot.module.system.model.vo.user.*;
import com.wick.boot.module.system.service.SystemUserAbstractService;
import com.wick.boot.module.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
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
 * @author Wickson
 * @date 2024-04-02
 */
@Slf4j
@Service
public class SystemUserServiceImpl extends SystemUserAbstractService implements SystemUserService {

    @Resource
    private RedisService redisService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public SystemUserDTO getUserInfo(String username) {
        // 根据用户名查询用户信息，并转换为 DTO 对象
        SystemUser systemUser = userMapper.selectByUsername(username);
        return SystemUserConvert.INSTANCE.entityToDTO(systemUser);
    }

    @Override
    public SystemUserLoginInfoDTO getCurrentUserInfo() {
        // Step 1: 获取当前登录用户信息
        LoginUserInfoDTO userDetails = SecurityUtils.getUserDetails();
        if (ObjUtil.isNull(userDetails)) {
            return SystemUserLoginInfoDTO.builder().build();
        }

        // Step 2: 查询用户详细信息并封装为 DTO
        SystemUser systemUser = userMapper.selectByUsername(userDetails.getUsername());
        SystemUserLoginInfoDTO userInfoDTO = SystemUserConvert.INSTANCE.entityToDTO1(systemUser);

        // Step 3: 设置用户的角色信息
        userInfoDTO.setRoles(userDetails.getRoles());

        // Step 4: 获取并设置用户的权限信息
        userInfoDTO.setPerms(getPerms(userDetails.getRoles()));
        return userInfoDTO;
    }

    /**
     * 根据角色代码获取权限菜单。
     *
     * @param roles 角色代码集合
     * @return 权限集合
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
    public PageResult<SystemUserDTO> getSystemUserPage(SystemUserQueryVO reqVO) {
        // 分页查询用户信息
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
    public void addSystemUser(SystemUserAddVO reqVO) {
        // Step 1: 验证新增用户参数
        this.validateAddParams(reqVO);

        // Step 2: 新增用户信息，并加密设置初始密码
        SystemUser systemUser = SystemUserConvert.INSTANCE.addVoToEntity(reqVO);
        String password = passwordEncoder.encode(GlobalConstants.DEFAULT_USER_PASSWORD);
        systemUser.setPassword(password);
        this.userMapper.insert(systemUser);

        // Step 3: 为用户分配角色
        assignUserRole(systemUser.getId(), reqVO.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSystemUser(SystemUserUpdateVO reqVO) {
        // Step 1: 验证更新用户参数
        this.validateUpdateParams(reqVO);

        // Step 2: 更新用户信息
        SystemUser systemUser = SystemUserConvert.INSTANCE.updateVoToEntity(reqVO);
        this.userMapper.updateById(systemUser);

        // Step 3: 更新用户角色关系
        assignUserRole(systemUser.getId(), reqVO.getRoleIds());
    }

    /**
     * 为用户分配角色。
     *
     * @param userId    用户ID
     * @param targetIds 目标角色ID集合
     */
    private void assignUserRole(Long userId, Set<Long> targetIds) {
        // 获取用户当前角色列表
        List<SystemUserRole> userRoles = this.userRoleMapper.selectListByUserId(userId);
        userRoles = CollUtil.emptyIfNull(userRoles);

        // 提取已有角色ID集合
        Set<Long> sourceIds = userRoles.stream().map(SystemUserRole::getRoleId).collect(Collectors.toSet());

        // 添加新分配的角色
        Collection<Long> saveIds = CollectionUtil.subtract(targetIds, sourceIds);
        if (CollUtil.isNotEmpty(saveIds)) {
            List<SystemUserRole> saveUserRoleList = saveIds.stream()
                    .map(roleId -> new SystemUserRole(userId, roleId))
                    .collect(Collectors.toList());
            this.userRoleMapper.insertBatch(saveUserRoleList);
        }

        // 移除不再分配的角色
        Collection<Long> deleteIds = CollectionUtil.subtract(sourceIds, targetIds);
        if (CollUtil.isNotEmpty(deleteIds)) {
            this.userRoleMapper.deleteBatchByUserIdAndRoleIds(userId, Sets.newHashSet(deleteIds));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSystemUser(List<Long> ids) {
        // Step 1: 校验删除用户参数
        List<SystemUser> systemUsers = this.userMapper.selectBatchIds(ids);
        this.validateDeleteParams(systemUsers, ids);

        // Step 2: 删除用户信息
        this.userMapper.deleteBatchIds(systemUsers);

        // Step 3: 删除用户的角色信息
        this.userRoleMapper.deleteBatchByUserIds(ids);
    }

    @Override
    public SystemUserDTO getSystemUser(Long id) {
        // Step 1: 查询用户信息
        SystemUser systemUser = userMapper.selectById(id);
        SystemUserDTO systemUserDTO = SystemUserConvert.INSTANCE.entityToDTO(systemUser);

        // Step 2: 查询并设置用户角色ID集合
        List<SystemUserRole> userRoles = this.userRoleMapper.selectListByUserId(systemUser.getId());
        List<Long> roleIds = userRoles.stream().map(SystemUserRole::getRoleId).collect(Collectors.toList());
        systemUserDTO.setRoleIds(roleIds);
        return systemUserDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPwd(Long userId, String password) {
        // Step 1: 校验更新密码参数
        SystemUser systemUser = this.userMapper.selectById(userId);
        this.validateUpdateByPwdParams(systemUser);

        // Step 2: 更新用户密码信息
        systemUser.setPassword(passwordEncoder.encode(password));
        this.userMapper.updateById(systemUser);
    }

    @Override
    public void downloadTemplate(HttpServletResponse response) {
        // 下载用户导入模板
        String fileName = "用户导入模板.xlsx";
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            String fileClassPath = "templates" + File.separator + "excel" + File.separator + fileName;
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileClassPath);
            ServletOutputStream outputStream = response.getOutputStream();
            ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            excelWriter.finish();
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.USER_DOWNLOAD_ERROR);
        }
    }

    @Override
    public void exportSystemUser(SystemUserQueryVO queryParams, HttpServletResponse response) {
        // 导出用户信息到 Excel 文件
        String fileName = "用户列表.xlsx";
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            List<SystemUserExportVO> exportUserList = this.userMapper.listExportUsers(queryParams);
            EasyExcel.write(response.getOutputStream(), SystemUserExportVO.class).sheet("用户列表").doWrite(exportUserList);
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.USER_EXPORT_ERROR);
        }
    }

    @Override
    public void importSystemUser(Long deptId, MultipartFile file) {
        // 导入用户信息
        UserImportListener listener = new UserImportListener(deptId);
        try {
            // TODO: 后续通过邮件通知导入结果
            EasyExcel.read(file.getInputStream(), SystemUserImportVO.class, listener);
        } catch (IOException e) {
            throw ServiceException.getInstance(ErrorCodeSystem.USER_IMPORT_ERROR);
        }
    }
}