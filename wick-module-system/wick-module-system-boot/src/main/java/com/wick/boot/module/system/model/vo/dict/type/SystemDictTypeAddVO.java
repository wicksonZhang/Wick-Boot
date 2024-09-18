package com.wick.boot.module.system.model.vo.dict.type;

import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.validator.InEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Wickson
 * @date 2024-04-15
 */
@Setter
@Getter
@ApiModel(value = "SystemDictTypeAddVO", description = "新增字段类型参数")
public class SystemDictTypeAddVO implements Serializable {

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码", required = true, example = "status")
    @NotBlank(message = "字典编码不能为空")
    private String code;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典编码", required = true, example = "status")
    @NotBlank(message = "字典编码不能为空")
    private String name;

    /**
     * 字典状态
     */
    @ApiModelProperty(value = "字典状态", required = true, example = "1")
    @InEnum(value = CommonStatusEnum.class, message = "字典状态必须是 {value}")
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
