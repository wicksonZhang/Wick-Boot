package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.wickson.security.commons.constant.SystemConstants;
import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.app.service.ISystemRoleService;
import cn.wickson.security.system.convert.SystemRoleConvert;
import cn.wickson.security.system.mapper.ISystemRoleMapper;
import cn.wickson.security.system.model.dto.SystemRoleDTO;
import cn.wickson.security.system.model.entity.SystemRole;
import cn.wickson.security.system.model.vo.QueryRolePageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台管理 - 角色信息
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<ISystemRoleMapper, SystemRole> implements ISystemRoleService {

    @Resource
    private ISystemRoleMapper roleMapper;

    @Override
    public PageResult<SystemRoleDTO> getRolePage(QueryRolePageReqVO reqVO) {
        Page<SystemRole> pageResult = roleMapper.selectPage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                new LambdaQueryWrapper<SystemRole>()
                        .likeRight(ObjUtil.isNotNull(reqVO.getName()), SystemRole::getName, reqVO.getName())
                        .likeRight(ObjUtil.isNotNull(reqVO.getCode()), SystemRole::getCode, reqVO.getCode())
                        .ne(SystemRole::getCode, SystemConstants.ROOT_ROLE_CODE)
        );
        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return PageResult.empty();
        }
        List<SystemRoleDTO> roleDTOList = SystemRoleConvert.INSTANCE.entityToDTOS(pageResult.getRecords());
        return new PageResult<>(roleDTOList, pageResult.getTotal());
    }

}
