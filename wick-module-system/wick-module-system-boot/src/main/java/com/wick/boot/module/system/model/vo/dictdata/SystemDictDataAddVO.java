package com.wick.boot.module.system.model.vo.dictdata;

import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.validator.InEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel(value = "SystemDictDataAddVO", description = "新增字段类型参数")
public class SystemDictDataAddVO {

    @ApiModelProperty(value = "字典类型编码", required = true, example = "gender")
    @NotBlank(message = "字典类型编码不能为空")
    private String dictCode;

    @Schema(description = "字典值", required = true, example = "1")
    @NotBlank(message = "字典值不能为空")
    private String value;

    @ApiModelProperty(value = "字典标签", required = true, example = "男")
    @NotBlank(message = "字典标签不能为空")
    private String label;

    @ApiModelProperty(value = "字典类型", example = "success")
    private String tagType;

    @ApiModelProperty(value = "状态(1: 启用, 0: 禁用)", required = true, example = "1")
    @InEnum(value = CommonStatusEnum.class, message = "字典数据状态必须是 {value}")
    private Integer status;

    @ApiModelProperty(value = "排序", example = "1")
    @Max(value = 99, message = "排序长度不能超过 99")
    private Integer sort;

    @ApiModelProperty(value = "备注", example = "备注")
    private String remark;

}
