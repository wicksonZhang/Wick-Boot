package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.app.service.AbstractSystemRoleAppService;
import com.wick.boot.module.system.app.service.ISystemRoleMenuService;
import com.wick.boot.module.system.app.service.ISystemRoleService;
import com.wick.boot.module.system.convert.SystemRoleConvert;
import com.wick.boot.module.system.model.dto.SystemRoleDTO;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.vo.role.AddRoleVo;
import com.wick.boot.module.system.model.vo.role.QueryRolePageReqVO;
import com.wick.boot.module.system.model.vo.role.UpdateRoleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理-服务实现层
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Service
public class SystemRoleServiceImpl extends AbstractSystemRoleAppService implements ISystemRoleService {

    @Resource
    private ISystemRoleMenuService roleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addRole(AddRoleVo reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增角色信息 */
        SystemRole systemRole = SystemRoleConvert.INSTANCE.addVoToEntity(reqVO);
        roleMapper.insert(systemRole);
        return systemRole.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(UpdateRoleVo reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 更新角色信息 */
        SystemRole systemRole = SystemRoleConvert.INSTANCE.updateVoToEntity(reqVO);
        this.roleMapper.updateById(systemRole);

        /* Step-3: 刷新 Role_Menu 权限信息 */
        this.roleMenuService.refreshRolePermsCache();
    }

    @Override
    public PageResult<SystemRoleDTO> getRolePage(QueryRolePageReqVO reqVO) {
        Page<SystemRole> pageResult = roleMapper.selectRolePage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getCode()
        );

        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return PageResult.empty();
        }

        List<SystemRoleDTO> roleDTOList = SystemRoleConvert.INSTANCE.entityToDTOS(pageResult.getRecords());
        return new PageResult<>(roleDTOList, pageResult.getTotal());
    }

}
