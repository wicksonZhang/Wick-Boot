package cn.wickson.security.system.app.service;


import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.model.dto.SystemUserDTO;
import cn.wickson.security.system.model.vo.QueryUserPageReqVO;

public interface ISystemUserService {

    /**
     * 通过用户名称获取用户信息
     *
     * @param username 用户名
     * @return SystemUserDTO
     */
    SystemUserDTO getUserInfo(String username);

    /**
     * 返回用户分页数据信息
     *
     * @param reqVO 用户分页查询请求数据
     * @return PageResult<SystemUserDTO>
     */
    PageResult<SystemUserDTO> getUserPage(QueryUserPageReqVO reqVO);
}
