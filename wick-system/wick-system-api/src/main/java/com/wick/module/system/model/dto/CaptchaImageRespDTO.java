package com.wick.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 验证码返回VO
 *
 * @author ZhangZiHeng
 * @date 2024-03-27
 */
@Data
@Builder
@ApiModel(value = "CaptchaImageRespDTO对象", description = "后台管理-验证码信息")
public class CaptchaImageRespDTO {

    @ApiModelProperty(value = "是否开启验证码", example = "true")
    private Boolean enable;

    @ApiModelProperty(value = "captchaKey", example = "8ad797d7511f49228526ed15c7dab118")
    private String captchaKey;

    @ApiModelProperty(value = "Base64 图片信息")
    private String captchaBase64;

    public static CaptchaImageRespDTO getInstance(Boolean enable, String captchaKey, String captchaBase64) {
        return new CaptchaImageRespDTO(enable, captchaKey, captchaBase64);
    }

}
