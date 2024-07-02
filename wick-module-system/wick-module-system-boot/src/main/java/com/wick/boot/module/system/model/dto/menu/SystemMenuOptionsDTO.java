package com.wick.boot.module.system.model.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单管理
 *
 * @author ZhangZiHeng
 * @date 2024-04-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemMenuOptionsDTO {

    @ApiModelProperty(value = "菜单ID", example = "1")
    private Long value;

    @ApiModelProperty(value = "菜单名称", example = "Nexus")
    private String label;

    @ApiModelProperty(value = "子级菜单信息")
    private List<SystemMenuOptionsDTO> children;
    
}
