package cn.wickson.security.system.app.service;


import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.model.dto.SystemUserDTO;
import cn.wickson.security.system.model.entity.SystemUser;
import cn.wickson.security.system.model.vo.QueryUserPageReqVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户管理-服务层
 */
public interface ISystemUserService extends IService<SystemUser> {

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

    /**
     * 通过用户名获取对象
     *
     * @param username 用户名
     * @return
     */
    SystemUser getUserByName(String username);
}
