package com.wick.boot.module.system.model.dto.dictdata;

import com.wick.boot.common.core.enums.CommonStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 字典数据
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Data
@Builder
@ApiModel(value = "SystemDictDataDTO对象", description = "后台管理-字典数据信息")
public class SystemDictDataDTO {

    @ApiModelProperty(value = "字典数据编号", example = "1")
    private Long id;

    @ApiModelProperty(value = "字典编码Code", example = "gender")
    private String typeCode;

    @ApiModelProperty(value = "字典名称", example = "男")
    private String name;

    @ApiModelProperty(value = "字典值", example = "1")
    private String value;

    /**
     * 状态 {@link CommonStatusEnum}
     */
    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "备注", example = "备注")
    private String remark;

}
