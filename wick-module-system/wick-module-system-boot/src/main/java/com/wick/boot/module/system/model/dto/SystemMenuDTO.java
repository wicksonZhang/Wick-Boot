package com.wick.boot.module.system.model.dto;

import com.wick.boot.module.system.model.entity.SystemMenu;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * 后台管理-菜单返回DTO
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemMenuDTO extends SystemMenu {

    @ApiModelProperty(value = "子菜单集合", example = "")
    private List<SystemMenuDTO> children;

    @ApiModelProperty(value = "路由权限", example = "ADMIN")
    private List<String> roles;

}
