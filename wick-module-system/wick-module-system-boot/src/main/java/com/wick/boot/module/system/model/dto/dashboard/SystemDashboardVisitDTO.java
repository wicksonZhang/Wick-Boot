package com.wick.boot.module.system.model.dto.dashboard;

import io.swagger.annotations.ApiModel;
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

    /**
     * 查询今日访问量
     */
    private Integer todayVisits;

    /**
     * 查询总访问量
     */
    private Integer totalVisits;

    /**
     * 查询昨日访问量
     */
    private Integer yesterdayVisits;

    /**
     * 增长率
     */
    private BigDecimal growthRate;

    public BigDecimal getGrowthRate() {
        if (yesterdayVisits != null && yesterdayVisits > 0) {
            return BigDecimal.valueOf(todayVisits).subtract(BigDecimal.valueOf(yesterdayVisits))
                    .divide(BigDecimal.valueOf(yesterdayVisits), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        } else {
            return BigDecimal.ZERO; // 如果昨日访问量为0或null，则增长率为0
        }
    }
}
