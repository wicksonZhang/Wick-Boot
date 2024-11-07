package com.wick.boot.module.monitor.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.monitor.mapper.MonitorJobMapper;
import com.wick.boot.module.monitor.model.entity.MonitorJob;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobAddVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobUpdateVO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务调度管理-防腐层
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
public abstract class MonitorJobAbstractService {

    @Resource
    private MonitorJobMapper monitorJobMapper;

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(MonitorJobAddVO reqVO) {

    }

    /**
     * 校验更新参数
     *
     * @param reqVO 新增参数
     */
    protected void validateUpdateParams(MonitorJobUpdateVO reqVO) {

    }

    /**
     * 校验删除参数
     *
     * @param monitorJobList 新增集合参数
     * @param ids                   主键集合
     */
    protected void validateDeleteParams(List<MonitorJob> monitorJobList, List<Long> ids) {
        // 验证定时任务调度否存在
        this.validateMonitorJobList(monitorJobList);
        // 验证定时任务调度集合和 ids 是否匹配
        this.validateMonitorJobByIds(monitorJobList, ids);
    }

    private void validateMonitorJobList(List<MonitorJob> monitorJobList) {
        // 验证定时任务调度否存在
        if (CollUtil.isEmpty(monitorJobList)) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID);
        }
    }

    private void validateMonitorJobByIds(List<MonitorJob> monitorJobList, List<Long> ids) {
        List<Long> monitorJobIds = monitorJobList.stream().map(MonitorJob::getId).collect(Collectors.toList());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, monitorJobIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID);
        }
    }
}