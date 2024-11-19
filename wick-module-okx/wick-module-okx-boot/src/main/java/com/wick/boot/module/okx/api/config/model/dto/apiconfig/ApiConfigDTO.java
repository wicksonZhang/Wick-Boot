package com.wick.boot.module.okx.api.config.model.dto.apiconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

import java.io.Serializable;

/**
 * Api配置-DTO
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
@Getter
@Setter
@ApiModel(value = "ApiConfigDTO对象", description = "Api配置视图DTO")
public class ApiConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配置编号")
    private Long id;

    @ApiModelProperty(value = "api_key")
    private String apiKey;

    @ApiModelProperty(value = "secret_key")
    private String secretKey;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "api备注名称")
    private String title;

}


