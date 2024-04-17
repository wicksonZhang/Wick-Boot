package com.wick.boot.module.system.model.vo.dict.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 字典数据更新VO
 *
 * @author ZhangZiHeng
 * @date 2024-04-17
 */
@Setter
@Getter
@ApiModel(value = "UpdateDictDataReqVO", description = "字典数据更新参数")
public class UpdateDictDataReqVO extends AddDictDataReqVO {

    /**
     * 字典数据主键ID
     */
    @ApiModelProperty(value = "字典数据主键ID", required = true, example = "1")
    @NotNull(message = "字典数据主键ID不能为空")
    private Long id;

}
