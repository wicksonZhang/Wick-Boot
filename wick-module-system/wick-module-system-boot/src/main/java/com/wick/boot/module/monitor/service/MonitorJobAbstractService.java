package com.wick.boot.module.monitor.service;

import com.wick.boot.module.monitor.model.vo.job.MonitorJobAddVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobUpdateVO;
import org.springframework.scheduling.support.CronExpression;

/**
 * 定时任务调度管理-防腐层
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
public abstract class MonitorJobAbstractService {

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(MonitorJobAddVO reqVO) {
        // 验证调用方法是否存在

        // 验证 cron 表达式是否正确
        this.validateCronExpression(reqVO.getCronExpression());
    }

    private void validateCronExpression(String cronExpression) {
        if (!CronExpression.isValidExpression(cronExpression)) {

        }
    }

    /**
     * 校验更新参数
     *
     * @param reqVO 新增参数
     */
    protected void validateUpdateParams(MonitorJobUpdateVO reqVO) {

    }

}