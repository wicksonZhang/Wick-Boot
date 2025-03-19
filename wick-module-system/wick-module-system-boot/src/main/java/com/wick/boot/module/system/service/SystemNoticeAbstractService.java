package com.wick.boot.module.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.wick.boot.module.system.mapper.SystemNoticeMapper;
import com.wick.boot.module.system.model.entity.SystemNotice;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeAddVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeUpdateVO;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知公告管理-防腐层
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
public abstract class SystemNoticeAbstractService {

    @Resource
    private SystemNoticeMapper systemNoticeMapper;

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(SystemNoticeAddVO reqVO) {

    }

    /**
     * 校验更新参数
     *
     * @param reqVO 新增参数
     */
    protected void validateUpdateParams(SystemNoticeUpdateVO reqVO) {

    }

    /**
     * 校验删除参数
     *
     * @param systemNoticeList 新增集合参数
     * @param ids                   主键集合
     */
    protected void validateDeleteParams(List<SystemNotice> systemNoticeList, List<Long> ids) {
        // 验证通知公告否存在
        this.validateSystemNoticeList(systemNoticeList);
        // 验证通知公告集合和 ids 是否匹配
        this.validateSystemNoticeByIds(systemNoticeList, ids);
    }

    private void validateSystemNoticeList(List<SystemNotice> systemNoticeList) {
        // 验证通知公告否存在
        if (CollUtil.isEmpty(systemNoticeList)) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID);
        }
    }

    private void validateSystemNoticeByIds(List<SystemNotice> systemNoticeList, List<Long> ids) {
        List<Long> systemNoticeIds = systemNoticeList.stream().map(SystemNotice::getId).collect(Collectors.toList());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, systemNoticeIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID);
        }
    }
}