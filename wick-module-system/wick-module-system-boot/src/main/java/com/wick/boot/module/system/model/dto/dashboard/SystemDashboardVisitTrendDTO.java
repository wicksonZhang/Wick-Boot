package com.wick.boot.module.system.model.dto.dashboard;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 访问趋势-折线图
 *
 * @author Wickson
 * @date 2024-10-24
 */
@Getter
@Setter
@ApiModel(description = "访问趋势-折线图")
public class SystemDashboardVisitTrendDTO {

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "浏览量(PV)")
    private Integer pv;

    @ApiModelProperty(value = "访客数(UV)")
    private Integer uv;

}
