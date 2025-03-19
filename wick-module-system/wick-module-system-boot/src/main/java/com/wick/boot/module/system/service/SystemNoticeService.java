package com.wick.boot.module.system.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeAddVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeUpdateVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeQueryVO;
import com.wick.boot.module.system.model.dto.notice.SystemNoticeDTO;

import java.util.List;
import java.util.Map;

/**
 * 通知公告-应用服务类
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
public interface SystemNoticeService {

    /**
     * 新增通知公告数据
     *
     * @param reqVO 新增请求参数
     */
    Long addSystemNotice(SystemNoticeAddVO reqVO);

    /**
     * 更新通知公告数据
     *
     * @param reqVO 更新请求参数
     */
    void updateSystemNotice(SystemNoticeUpdateVO reqVO);

    /**
     * 删除新增通知公告数据
     *
     * @param ids 主键集合
     */
    void deleteSystemNotice(List<Long> ids);

    /**
     * 通过主键获取通知公告数据
     *
     * @param id 通知公告ID
     * @return SystemNoticeDTO 通知公告DTO
     */
     SystemNoticeDTO getSystemNotice(Long id);

    /**
     * 获取通知公告分页数据
     *
     * @param queryParams 分页查询参数
     * @return SystemNoticeDTO 通知公告DTO
     */
    PageResult<SystemNoticeDTO> getSystemNoticePage(SystemNoticeQueryVO queryParams);

    /**
     * 获取我的通知公告分页数据
     *
     * @param reqVO
     * @return
     */
    Map<String, Object> getSystemNoticeMyPage(SystemNoticeQueryVO reqVO);
}