package cn.wickson.security.system.app.service;


import cn.wickson.security.system.model.dto.SystemUserDTO;

public interface ISystemUserService {

    /**
     * 通过用户名称获取用户信息
     *
     * @param username 用户名
     * @return
     */
    SystemUserDTO getUserInfo(String username);
}
