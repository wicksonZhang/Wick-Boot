package com.wick.boot.module.system.model.dto.dicttype;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典类型DTO
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Data
@Builder
@ApiModel(value = "SystemDictTypeDTO对象", description = "后台管理-字典类型信息")
public class SystemDictTypeDTO {

    @ApiModelProperty(value = "字典类型主键", example = "1")
    private Long id;

    @ApiModelProperty(value = "字典名称", example = "性别")
    private String name;

    @ApiModelProperty(value = "字典类型", example = "gender")
    private String dictCode;

    /**
     * 枚举 {@link CommonStatusEnum}
     */
    @ApiModelProperty(value = "字典状态(1->启用、0->禁用)", example = "1")
    private Integer status;

    @ApiModelProperty(value = "备注", example = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间" , example = "2024-04-06 22:11:44")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private LocalDateTime createTime;

}
