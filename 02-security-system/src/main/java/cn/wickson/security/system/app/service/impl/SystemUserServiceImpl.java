package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.app.service.ISystemUserService;
import cn.wickson.security.system.convert.SystemUserConvert;
import cn.wickson.security.system.mapper.ISystemUserMapper;
import cn.wickson.security.system.model.dto.SystemUserDTO;
import cn.wickson.security.system.model.entity.SystemUser;
import cn.wickson.security.system.model.vo.QueryUserPageReqVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<ISystemUserMapper, SystemUser> implements ISystemUserService {

    @Resource
    private ISystemUserMapper userMapper;

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
            return new PageResult<>(pageResult.getTotal());
        }
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal());
    }
}
