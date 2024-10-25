package com.wick.boot.module.system.model.vo.dashboard;

import cn.hutool.core.util.ObjectUtil;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ParameterException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 首页-访问趋势VO
 *
 * @author Wickson
 * @date 2024-10-24
 */
@Setter
@Getter
@ApiModel(value = "SystemDashboardQueryVO", description = "访问趋势查询条件参数")
public class SystemDashboardQueryVO {

    @ApiModelProperty(value = "查询开始时间", required = true, example = "2024-10-16")
    @NotNull(message = "开始时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "查询结束时间", required = true, example = "2024-10-24")
    @NotNull(message = "结束时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 参数合法性校验
     */
    public void validateParam() {
        this.dateCompare();
    }

    /**
     * 查询开始时间与结束时间的比较
     */
    private void dateCompare() {
        if (ObjectUtil.isNull(startDate) || ObjectUtil.isNull(endDate)) {
            if (ObjectUtil.isNotNull(startDate) || ObjectUtil.isNotNull(endDate)) {
                throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID);
            }
            return;
        }

        if (startDate.isAfter(endDate)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_START_DATE_GREATER_END_DATE_ERROR);
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days > 30) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_DATE_RANGE_ERROR);
        }
    }

}
