package com.wick.boot.module.monitor.service;

import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoAddVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoUpdateVO;

/**
 * 定时任务管理管理-防腐层
 *
 * @author Wickson
 * @date 2024-11-13 10:24
 */
public abstract class MonitorXxlJobInfoAbstractService {

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(MonitorXxlJobInfoAddVO reqVO) {

    }

    /**
     * 校验更新参数
     *
     * @param reqVO 新增参数
     */
    protected void validateUpdateParams(MonitorXxlJobInfoUpdateVO reqVO) {

    }

}