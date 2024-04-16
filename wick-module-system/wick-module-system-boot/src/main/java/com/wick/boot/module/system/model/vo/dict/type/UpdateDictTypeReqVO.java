package com.wick.boot.module.system.model.vo.dict.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 字典类型更新VO
 * @author ZhangZiHeng
 * @date 2024-04-16
 */
@Setter
@Getter
@ApiModel(value = "UpdateDictTypeReqVO", description = "字典类型更新参数")
public class UpdateDictTypeReqVO extends AddDictTypeReqVO {

    /**
     * 字典类型主键ID
     */
    @ApiModelProperty(value = "字典类型主键ID", required = true, example = "1")
    @NotNull(message = "字典类型主键ID不能为空")
    private Long id;

}
