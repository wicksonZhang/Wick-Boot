package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.app.service.ISystemRoleService;
import cn.wickson.security.system.convert.SystemRoleConvert;
import cn.wickson.security.system.mapper.ISystemRoleMapper;
import cn.wickson.security.system.model.dto.SystemRoleDTO;
import cn.wickson.security.system.model.entity.SystemRole;
import cn.wickson.security.system.model.vo.QueryRolePageReqVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
