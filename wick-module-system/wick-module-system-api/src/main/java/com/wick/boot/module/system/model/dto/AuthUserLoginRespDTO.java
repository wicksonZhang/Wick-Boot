package com.wick.boot.module.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "后台管理 - 登录 Response VO")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthUserLoginRespDTO {

    @ApiModelProperty(value = "访问token")
    private String accessToken;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

    @ApiModelProperty(value = "过期时间(单位：毫秒)")
    private Long expires;

    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType;

    public static AuthUserLoginRespDTO getInstance(String accessToken, String refreshToken) {
        return AuthUserLoginRespDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }
}
