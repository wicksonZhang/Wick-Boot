package com.wick.boot.module.tools.model.vo;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 代码生成器查询VO
 *
 * @author ZhangZiHeng
 * @date 2024-07-23
 */
@Setter
@Getter
@ApiModel(value = "QueryCodeGenTablePageReqVO", description = "代码查询参数VO")
public class QueryCodeGenTablePageReqVO extends CommonPageParamVO {

    /**
     * 数据表名
     */
    @ApiModelProperty(value = "数据表名", required = false, example = "system_user")
    private String name;

    /**
     * 数据表描述
     */
    @ApiModelProperty(value = "数据表描述", required = false, example = "用户信息表")
    private String comment;
}
