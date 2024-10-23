package com.wick.boot.module.tool.model.vo.table;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 代码生成器查询VO
 *
 * @author Wickson
 * @date 2024-07-23
 */
@Setter
@Getter
@ApiModel(value = "ToolCodeGenTableQueryVO", description = "代码查询参数VO")
public class ToolCodeGenTableQueryVO extends CommonPageParamVO {

    @ApiModelProperty(value = "数据源id", example = "1")
    private Long dataSourceId;

    @ApiModelProperty(value = "数据表名", required = false, example = "system_user")
    private String tableName;

    @ApiModelProperty(value = "数据表描述", required = false, example = "用户信息表")
    private String tableComment;

    @ApiModelProperty(value = "开始时间-结束时间", example = "[2024-06-01 00:00:00, 2024-06-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] createTime;

}
