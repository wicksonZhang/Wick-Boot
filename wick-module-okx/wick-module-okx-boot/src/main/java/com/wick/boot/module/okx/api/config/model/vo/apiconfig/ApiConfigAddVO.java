package com.wick.boot.module.okx.api.config.model.vo.apiconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Api配置新增参数
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
@Getter
@Setter
@ApiModel(value = "ApiConfigAddVO", description = "Api配置新增参数")
public class ApiConfigAddVO {

    @ApiModelProperty(value = "api_key")
    @NotBlank(message = "apiKey不能为空")
    private String apiKey;

    @ApiModelProperty(value = "secret_key")
    @NotBlank(message = "secretKey不能为空")
    private String secretKey;

    @ApiModelProperty(value = "api备注名称")
    @NotBlank(message = "api备注不能为空")
    private String title;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String passPhrase;
}


