package com.wick.boot.module.system.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.dto.LoginLogReqDTO;
import com.wick.boot.module.system.model.dto.logger.login.SystemLoginLogDTO;
import com.wick.boot.module.system.model.vo.logger.login.SystemLoginLogQueryVO;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录日志-服务层
 *
 * @author Wickson
 * @date 2024-06-04
 */
public interface SystemLoginLogService {

    /**
     * 创建登录日志
     *
     * @param logReqDTO 登录日志DTO
     */
    void createLoginLog(LoginLogReqDTO logReqDTO);

    /**
     * 分页查询登录日志
     *
     * @param reqVO 登录日志请求VO
     * @return 分页结果集
     */
    PageResult<SystemLoginLogDTO> getLoginLogPage(SystemLoginLogQueryVO reqVO);

    /**
     * 导出用户登录日志
     *
     * @param queryParams 登录日志请求VO
     * @param response    响应结果集
     */
    void exportLoginLog(SystemLoginLogQueryVO queryParams, HttpServletResponse response);
}
