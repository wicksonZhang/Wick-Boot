package cn.wickson.security.system.model.dto;

import cn.wickson.security.commons.enums.CommonStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 字典数据
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Data
@Builder
@ApiModel(value = "SystemDictDataDTO对象", description = "后台管理-字典数据信息")
public class SystemDictDataDTO {

    @ApiModelProperty(value = "字典数据编号", example = "1")
    private Long id;

    @ApiModelProperty(value = "字典名称", example = "男")
    private String name;

    @ApiModelProperty(value = "字典值", example = "1")
    private String value;

    /**
     * 状态 {@link CommonStatusEnum}
     */
    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

}
