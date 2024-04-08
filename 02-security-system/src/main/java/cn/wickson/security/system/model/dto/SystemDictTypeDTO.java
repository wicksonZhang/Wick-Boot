package cn.wickson.security.system.model.dto;

import cn.wickson.security.commons.enums.CommonStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author ZhangZiHeng
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
    private String code;

    /**
     * 枚举 {@link CommonStatusEnum}
     */
    @ApiModelProperty(value = "字典状态(1->启用、0->禁用)", example = "1")
    private Integer status;


}
