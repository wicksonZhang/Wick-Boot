package com.wick.boot.module.system.model.dto.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色管理
 *
 * @author ZhangZiHeng
 * @date 2024-05-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemRoleOptionsDTO {

    @ApiModelProperty(value = "角色ID", example = "2")
    private Long value;

    @ApiModelProperty(value = "角色名称", example = "系统管理员")
    private String label;

}
