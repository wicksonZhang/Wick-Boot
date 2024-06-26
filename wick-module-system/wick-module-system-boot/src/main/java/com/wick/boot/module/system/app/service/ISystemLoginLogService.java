package com.wick.boot.module.system.app.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.dto.LoginLogReqDTO;
import com.wick.boot.module.system.model.dto.SystemLoginLogDTO;
import com.wick.boot.module.system.model.vo.logger.login.QueryLoginLogPageReqVO;

/**
 * 登录日志-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
public interface ISystemLoginLogService {

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
    PageResult<SystemLoginLogDTO> getLoginLogPage(QueryLoginLogPageReqVO reqVO);
}
