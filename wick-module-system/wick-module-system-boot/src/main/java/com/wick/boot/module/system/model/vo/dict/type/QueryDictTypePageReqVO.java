package com.wick.boot.module.system.model.vo.dict.type;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Setter
@Getter
@ApiModel(value = "QueryDictTypePageReqVO", description = "菜单信息查询条件参数")
public class QueryDictTypePageReqVO extends CommonPageParamVO {

    @ApiModelProperty(value = "字典名称", example = "性别")
    private String name;

    @ApiModelProperty(value = "字典编码", example = "gender")
    private String code;

}
