package com.wick.boot.module.system.model.dto.dashboard;

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

    @ApiModelProperty(value = "统计类型")
    private String type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "今日访问量")
    private Integer todayCount;

    @ApiModelProperty(value = "总访问量")
    private Integer totalCount;

    @ApiModelProperty(value = "增长率")
    private BigDecimal growthRate;

    @ApiModelProperty(value = "统计粒度标签")
    private String granularityLabel;

}
