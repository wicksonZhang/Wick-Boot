package com.wick.boot.module.system.model.vo.dict.data;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 字典数据
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Setter
@Getter
@ApiModel(value = "SystemDictDataQueryVO", description = "字典数据信息查询条件参数")
public class SystemDictDataQueryVO extends CommonPageParamVO {

    @ApiModelProperty(value = "字典类型编码", required = true, example = "gender")
    @NotBlank(message = "字典编码不能为空")
    private String code;

    @ApiModelProperty(value = "字典数据名称", example = "男")
    private String name;

}
