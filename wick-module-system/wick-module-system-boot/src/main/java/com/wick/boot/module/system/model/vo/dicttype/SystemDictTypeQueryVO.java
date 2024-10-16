package com.wick.boot.module.system.model.vo.dicttype;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典类型查询VO
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Setter
@Getter
@ApiModel(value = "SystemDictTypeQueryVO", description = "菜单信息查询条件参数")
public class SystemDictTypeQueryVO extends CommonPageParamVO {

    @ApiModelProperty(value = "字典名称", example = "性别")
    private String name;

    @ApiModelProperty(value = "字典编码", example = "gender")
    private String code;

}
