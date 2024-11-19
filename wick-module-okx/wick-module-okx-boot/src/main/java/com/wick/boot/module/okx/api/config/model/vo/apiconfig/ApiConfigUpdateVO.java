package com.wick.boot.module.okx.api.config.model.vo.apiconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * Api配置更新参数类
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
@Setter
@Getter
@ApiModel(value = "ApiConfigUpdateVO对象", description = "Api配置更新参数")
public class ApiConfigUpdateVO extends ApiConfigAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", required = true, example = "1")
    @NotNull(message = "主键ID不能为空")
    private Long id;
}
