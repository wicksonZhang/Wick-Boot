package com.wick.boot.module.system.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;
import com.wick.boot.module.system.model.dto.logger.operate.SystemOperateLogDTO;
import com.wick.boot.module.system.model.vo.logger.operate.QueryOperateLogPageReqVO;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录日志-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
public interface SystemOperateLogService {

    /**
     * 创建操作日志
     *
     * @param createReqDTO 请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

    /**
     * 获取操作日志分页
     *
     * @param reqVO 请求参数VO
     * @return
     */
    PageResult<SystemOperateLogDTO> getOperateLogPage(QueryOperateLogPageReqVO reqVO);

    /**
     * 导出用户操作日志
     *
     * @param queryParams 请求参数VO
     * @param response    响应结果集
     */
    void exportOperateLog(QueryOperateLogPageReqVO queryParams, HttpServletResponse response);
}
