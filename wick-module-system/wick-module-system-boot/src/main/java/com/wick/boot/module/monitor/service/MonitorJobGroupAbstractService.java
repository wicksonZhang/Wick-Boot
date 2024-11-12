package com.wick.boot.module.monitor.service;

import cn.hutool.core.util.StrUtil;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupAddVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupUpdateVO;
import com.wick.boot.module.system.enums.monitor.JobGroupAddressTypeEnum;
import org.springframework.scheduling.support.CronExpression;

/**
 * 定时任务调度管理-防腐层
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
public abstract class MonitorJobGroupAbstractService {

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(MonitorJobGroupAddVO reqVO) {
        // 校验 addressList 参数
        this.validateAddressList(reqVO);
    }

    /**
     * 校验机器地址
     */
    private void validateAddressList(MonitorJobGroupAddVO reqVO) {
        Integer addressType = reqVO.getAddressType();
        String addressList = reqVO.getAddressList();

        // 自动注册时清空机器地址
        if (JobGroupAddressTypeEnum.AUTOMATIC_ENTRY.getValue().equals(addressType)) {
            reqVO.setAddressList(null);
            return;
        }

        // 手动录入时，机器地址不能为空
        if (JobGroupAddressTypeEnum.MANUAL_ENTRY.getValue().equals(addressType) && StrUtil.isBlank(addressList)) {
            throw ServiceException.getInstance(
                    GlobalResultCodeConstants.PARAM_IS_INVALID.getCode(),
                    "机器地址不能为空"
            );
        }
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
    protected void validateUpdateParams(MonitorJobGroupUpdateVO reqVO) {

    }

}