package com.wick.boot.module.okx.api.config.model.vo.apiconfig;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Api配置分页查询参数
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
@Setter
@Getter
@ApiModel(value = "ApiConfigQueryVO", description = "Api配置分页查询参数")
public class ApiConfigQueryVO extends CommonPageParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "api备注名称")
    private String title;

}
