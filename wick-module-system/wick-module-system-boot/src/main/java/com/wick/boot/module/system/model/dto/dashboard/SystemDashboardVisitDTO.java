package com.wick.boot.module.system.model.dto.dashboard;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 访问结果
 *
 * @author Wickson
 * @date 2024-10-24
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(description = "访问量统计")
public class SystemDashboardVisitDTO {

    @ApiModelProperty(value = "今日访问量")
    private Integer todayUvCount;

    @ApiModelProperty(value = "访客数量总数")
    private Integer totalUvCount;

    @ApiModelProperty(value = "昨日访问量")
    private Integer yesterdayUvCount;

    @ApiModelProperty(value = "访问量增长率")
    private BigDecimal uvGrowthRate;

    @ApiModelProperty(value = "今日浏览量")
    private Integer todayPvCount;

    @ApiModelProperty(value = "浏览量总数")
    private Integer totalPvCount;

    @ApiModelProperty(value = "昨日浏览量")
    private Integer yesterdayPvCount;

    @ApiModelProperty(value = "浏览量增长率")
    private BigDecimal pvGrowthRate;

    public BigDecimal getUvGrowthRate() {
        return getGrowthRate(todayUvCount, yesterdayUvCount);
    }

    public BigDecimal getPvGrowthRate() {
        return getGrowthRate(todayPvCount, yesterdayPvCount);
    }

    public BigDecimal getGrowthRate(Integer todayVisits, Integer yesterdayVisits) {
        if (yesterdayVisits != null && yesterdayVisits > 0) {
            return BigDecimal.valueOf(todayVisits)
                    .subtract(BigDecimal.valueOf(yesterdayVisits))
                    .divide(BigDecimal.valueOf(yesterdayVisits), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        } else {
            return BigDecimal.ZERO; // 如果昨日访问量为0或null，则增长率为0
        }
    }
}
