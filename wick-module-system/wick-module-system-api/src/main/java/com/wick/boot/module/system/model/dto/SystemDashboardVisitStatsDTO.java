package com.wick.boot.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 访问量统计
 *
 * @author Wickson
 * @date 2024-10-24
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(description = "访问量统计")
public class SystemDashboardVisitStatsDTO {

    @ApiModelProperty(value = "今日访问量")
    private Integer todayUvCount;

    @ApiModelProperty(value = "访客数量总数")
    private Integer totalUvCount;

    @ApiModelProperty(value = "访问量增长率")
    private Double uvGrowthRate;

    @ApiModelProperty(value = "今日浏览量")
    private Integer todayPvCount;

    @ApiModelProperty(value = "浏览量总数")
    private Integer totalPvCount;

    @ApiModelProperty(value = "浏览量增长率")
    private Double pvGrowthRate;

}
