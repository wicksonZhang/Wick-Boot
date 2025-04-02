package com.wick.boot.module.system.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserOptionDTO {

    @ApiModelProperty(value = "菜单ID", example = "1")
    private Long value;

    @ApiModelProperty(value = "菜单名称", example = "Nexus")
    private String label;

}
