package com.wick.boot.module.system.service;


import com.wick.boot.module.system.model.dto.user.SystemUserInfoDTO;
import com.wick.boot.module.system.model.dto.user.SystemUserDTO;
import com.wick.boot.module.system.model.vo.user.*;
import com.wick.boot.common.core.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户管理-服务层
 */
public interface SystemUserService {

    /**
     * 通过用户名称获取用户信息
     *
     * @param username 用户名
     * @return SystemUserDTO
     */
    SystemUserDTO getUserInfo(String username);

    /**
     * 获取当前用户信息
     *
     * @return SystemUserInfoDTO
     */
    SystemUserInfoDTO getCurrentUserInfo();

    /**
     * 返回用户分页数据信息
     *
     * @param reqVO 用户分页查询请求数据
     * @return PageResult<SystemUserDTO>
     */
    PageResult<SystemUserDTO> getUserPage(QueryUserPageReqVO reqVO);

    /**
     * 新增用户信息
     *
     * @param reqVO 用户新增参数
     */
    void addUser(AddUserVO reqVO);

    /**
     * 更新用户信息
     *
     * @param reqVO 用户更新参数
     */
    void updateUser(UpdateUserVO reqVO);

    /**
     * 删除用户信息
     *
     * @param ids 用户id
     */
    void deleteUser(List<Long> ids);

    /**
     * 通过用户ID获取用户信息
     *
     * @param id 用户id
     * @return SystemUserDTO
     */
    SystemUserDTO getUserById(Long id);

    /**
     * 更新用户密码信息
     *
     * @param reqVO 更新用户密码参数信息
     */
    void resetPwd(UpdateUserPwdVO reqVO);

    /**
     * 获得用户列表
     *
     * @return
     */
    List<SystemUserDTO> simpleList();

    /**
     * 用户导入模板下载
     *
     * @param response resp 响应结果集
     */
    void downloadTemplate(HttpServletResponse response);

    /**
     * 导出用户
     *
     * @param queryParams 用户分页查询请求数据
     * @param response    响应结果集
     */
    void exportUsers(QueryUserPageReqVO queryParams, HttpServletResponse response);

    /**
     * 导入用户
     *
     * @param deptId 部门Id
     * @param file   文件信息
     * @return 响应信息
     */
    void importUsers(Long deptId, MultipartFile file);
}
