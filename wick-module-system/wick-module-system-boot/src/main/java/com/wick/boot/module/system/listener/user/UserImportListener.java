package com.wick.boot.module.system.listener.user;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.module.system.convert.SystemUserConvert;
import com.wick.boot.module.system.enums.GenderTypeEnum;
import com.wick.boot.module.system.mapper.ISystemRoleMapper;
import com.wick.boot.module.system.mapper.ISystemUserMapper;
import com.wick.boot.module.system.mapper.ISystemUserRoleMapper;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.entity.SystemUserRole;
import com.wick.boot.module.system.model.vo.user.UserImportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户导入监听器
 * <p>
 * <a href="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read#%E6%9C%80%E7%AE%80%E5%8D%95%E7%9A%84%E8%AF%BB%E7%9A%84%E7%9B%91%E5%90%AC%E5%99%A8">最简单的读的监听器</a>
 */
@Slf4j
public class UserImportListener extends AnalysisEventListener<UserImportVO> {


    // 有效条数
    private int validCount;

    // 无效条数
    private int invalidCount;

    // 导入返回信息
    StringBuilder msg = new StringBuilder();

    // 部门ID
    private final Long deptId;

    private final ISystemUserMapper userMapper;

    private final ISystemRoleMapper roleMapper;

    private final ISystemUserRoleMapper userRoleMapper;

    private final PasswordEncoder passwordEncoder;

    private final SystemUserConvert userConvert;

    public UserImportListener(Long deptId) {
        this.deptId = deptId;
        this.userMapper = SpringUtil.getBean(ISystemUserMapper.class);
        this.roleMapper = SpringUtil.getBean(ISystemRoleMapper.class);
        this.userRoleMapper = SpringUtil.getBean(ISystemUserRoleMapper.class);
        this.passwordEncoder = SpringUtil.getBean(PasswordEncoder.class);
        this.userConvert = SpringUtil.getBean(SystemUserConvert.class);
    }

    /**
     * 每一条数据解析都会来调用
     * <p>
     * 1. 数据校验；全字段校验
     * 2. 数据持久化；
     *
     * @param userImportVO    一行数据，类似于 {@link AnalysisContext#readRowHolder()}
     * @param analysisContext
     */
    @Override
    public void invoke(UserImportVO userImportVO, AnalysisContext analysisContext) {
        log.info("解析到一条用户数据:{}", JSONUtil.toJsonStr(userImportVO));
        // 校验数据
        StringBuilder validationMsg = new StringBuilder();
        // 校验用户名
        String username = userImportVO.getUsername();
        if (StrUtil.isBlank(username)) {
            validationMsg.append("用户名为空；");
        } else {
            SystemUser systemUser = this.userMapper.selectByUsername(username);
            if (ObjUtil.isNotNull(systemUser)) {
                validationMsg.append("用户名已存在；");
            }
        }

        // 校验用户昵称
        String nickname = userImportVO.getNickname();
        if (StrUtil.isBlank(nickname)) {
            validationMsg.append("用户昵称为空；");
        }

        // 校验用户手机号
        String mobile = userImportVO.getMobile();
        if (StrUtil.isBlank(mobile)) {
            validationMsg.append("手机号码为空；");
        } else {
            if (!Validator.isMobile(mobile)) {
                validationMsg.append("手机号码不正确；");
            }
        }

        if (StrUtil.isBlank(validationMsg)) {
            // 校验通过，持久化至数据库
            SystemUser entity = userConvert.importVo2Entity(userImportVO);
            entity.setDeptId(deptId);   // 部门
            entity.setPassword(passwordEncoder.encode(GlobalConstants.DEFAULT_USER_PASSWORD));   // 默认密码
            // 性别翻译
            String genderLabel = userImportVO.getGenderLabel();
            if (StrUtil.isNotBlank(genderLabel)) {
                GenderTypeEnum typeEnum = GenderTypeEnum.valueOf(genderLabel);
                entity.setGender(typeEnum.getCode());
            }

            // 角色解析
            String roleCodes = userImportVO.getRoleCodes();
            List<Long> roleIds = null;
            if (StrUtil.isNotBlank(roleCodes)) {
                roleIds = roleMapper.selectList(
                                new LambdaQueryWrapper<SystemRole>()
                                        .in(SystemRole::getCode, (Object) roleCodes.split(","))
                                        .eq(SystemRole::getStatus, CommonStatusEnum.ENABLE.getValue())
                                        .select(SystemRole::getId)
                        ).stream()
                        .map(SystemRole::getId)
                        .collect(Collectors.toList());
            }

            int insert = userMapper.insert(entity);
            if (insert > 0) {
                validCount++;
                // 保存用户角色关联
                if (CollectionUtil.isNotEmpty(roleIds)) {
                    List<SystemUserRole> userRoles = roleIds.stream()
                            .map(roleId -> new SystemUserRole(entity.getId(), roleId))
                            .collect(Collectors.toList());
                    userRoleMapper.insertBatch(userRoles);
                }
            } else {
                invalidCount++;
                msg.append("第").append(validCount + invalidCount).append("行数据保存失败；<br/>");
            }
        } else {
            invalidCount++;
            msg.append("第").append(validCount + invalidCount).append("行数据校验失败：").append(validationMsg).append("<br/>");
        }
    }

    /**
     * 所有数据解析完成会来调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

}
