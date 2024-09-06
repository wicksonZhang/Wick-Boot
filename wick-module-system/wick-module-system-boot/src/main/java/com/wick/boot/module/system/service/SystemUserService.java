package com.wick.boot.module.system.service;


import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.dto.user.SystemUserDTO;
import com.wick.boot.module.system.model.dto.user.SystemUserLoginInfoDTO;
import com.wick.boot.module.system.model.vo.user.SystemUserQueryVO;
import com.wick.boot.module.system.model.vo.user.SystemUserAddVO;
import com.wick.boot.module.system.model.vo.user.SystemUserUpdateVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户管理服务接口，用于定义用户相关操作的服务层方法。
 *
 * @author Wickson
 * @date 2024-04-02
 */
public interface SystemUserService {

    /**
     * 根据用户名获取用户详细信息。
     *
     * @param username 用户名
     * @return 包含用户详细信息的 SystemUserDTO 对象
     */
    SystemUserDTO getUserInfo(String username);

    /**
     * 获取当前登录用户的基本信息。
     *
     * @return 当前用户的基本信息封装为 SystemUserLoginInfoDTO 对象
     */
    SystemUserLoginInfoDTO getCurrentUserInfo();

    /**
     * 分页获取用户列表，根据请求参数查询并返回用户分页数据。
     *
     * @param reqVO 包含分页查询条件的请求对象
     * @return 分页结果，包含用户信息列表的 PageResult 对象
     */
    PageResult<SystemUserDTO> getSystemUserPage(SystemUserQueryVO reqVO);

    /**
     * 添加新用户。
     *
     * @param reqVO 包含新用户信息的请求对象
     */
    void addSystemUser(SystemUserAddVO reqVO);

    /**
     * 更新用户信息。
     *
     * @param reqVO 包含要更新的用户信息的请求对象
     */
    void updateSystemUser(SystemUserUpdateVO reqVO);

    /**
     * 删除指定用户信息。
     *
     * @param ids 要删除的用户ID列表
     */
    void deleteSystemUser(List<Long> ids);

    /**
     * 根据用户ID获取用户信息。
     *
     * @param id 用户ID
     * @return 包含用户详细信息的 SystemUserDTO 对象
     */
    SystemUserDTO getSystemUser(Long id);

    /**
     * 重置用户密码。
     *
     * @param userId   用户ID
     * @param password 新密码
     */
    void resetPwd(Long userId, String password);

    /**
     * 下载用户导入模板。
     *
     * @param response HTTP 响应，用于下载文件
     */
    void downloadTemplate(HttpServletResponse response);

    /**
     * 导出用户信息，根据查询条件导出用户列表。
     *
     * @param queryParams 包含导出条件的请求对象
     * @param response    HTTP 响应，用于下载文件
     */
    void exportSystemUser(SystemUserQueryVO queryParams, HttpServletResponse response);

    /**
     * 导入用户信息。
     *
     * @param deptId 部门ID，用于关联导入的用户
     * @param file   包含用户信息的上传文件
     */
    void importSystemUser(Long deptId, MultipartFile file);
}
