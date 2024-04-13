package com.wick.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wick.module.system.app.service.ISystemRoleService;
import com.wick.module.system.convert.SystemRoleConvert;
import com.wick.module.system.mapper.ISystemRoleMapper;
import com.wick.module.system.model.dto.SystemRoleDTO;
import com.wick.module.system.model.entity.SystemRole;
import com.wick.module.system.model.vo.QueryRolePageReqVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.common.core.result.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理-服务实现层
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Service
public class SystemRoleServiceImpl implements ISystemRoleService {

    @Resource
    private ISystemRoleMapper roleMapper;

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
