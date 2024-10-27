package com.wick.boot.module.monitor.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.monitor.model.dto.online.MonitorOnlineDTO;
import com.wick.boot.module.monitor.model.vo.online.MonitorOnlineQueryVO;

import javax.servlet.http.HttpServletResponse;

/**
 * 在线用户-应用服务类
 *
 * @author Wickson
 * @date 2024-10-25
 */
public interface MonitorOnlineService {

    /**
     * 获取在线用户分页数据
     *
     * @param queryVO 分页查询参数
     * @return MonitorOnlineDTO 操作日志记录DTO
     */
    PageResult<MonitorOnlineDTO> getMonitorOnlinePage(MonitorOnlineQueryVO queryVO);

    /**
     * 强制推出
     *
     * @param sessionId 会话ID
     */
    void forceQuit(String sessionId);

    /**
     * 导出在线用户
     *
     * @param queryParams 在线用户请求VO
     * @param response    响应结果集
     */
    void exportOnline(MonitorOnlineQueryVO queryParams, HttpServletResponse response);
}
