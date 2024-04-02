package cn.wickson.security.system.app.service.impl;

import cn.wickson.security.system.app.service.ISystemUserService;
import cn.wickson.security.system.convert.SystemUserConvert;
import cn.wickson.security.system.mapper.ISystemUserMapper;
import cn.wickson.security.system.model.dto.SystemUserDTO;
import cn.wickson.security.system.model.entity.SystemUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Service
public class SystemUserServiceImpl implements ISystemUserService {

    @Resource
    private ISystemUserMapper userMapper;

    @Override
    public SystemUserDTO getUserInfo(String username) {
        SystemUser systemUser = userMapper.selectByUsername(username);
        return SystemUserConvert.INSTANCE.entityToDTO(systemUser);
    }
    
}
