package com.wick.boot.module.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 后台管理 - 部门管理
 *
 * @author ZhangZiHeng
 * @date 2024-04-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemDeptOptionsDTO {

    @ApiModelProperty(value = "部门ID", example = "1")
    private Long value;

    @ApiModelProperty(value = "部门名称", example = "Nexus")
    private String label;

    @ApiModelProperty(value = "子级部门信息")
    private List<SystemDeptOptionsDTO> children;

}
