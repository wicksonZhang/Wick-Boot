package com.wick.boot.module.system.model.vo.logger.operate;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 系统管理 - 操作日志查询条件
 *
 * @author Wickson
 * @date 2024-06-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SystemOperateLogQueryVO", description = "操作日志查询条件参数")
public class SystemOperateLogQueryVO extends CommonPageParamVO {

    @ApiModelProperty(value = "用户编号", example = "1")
    private Long userId;

    @ApiModelProperty(value = "操作类型", example = "导出")
    private Integer type;

    @ApiModelProperty(value = "操作模块，模拟匹配", example = "订单")
    private String module;

    @ApiModelProperty(value = "操作名，模拟匹配", example = "创建订单")
    private String name;

    @ApiModelProperty(value = "开始时间", example = "[2024-06-01 00:00:00, 2024-06-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] createTime;

}
