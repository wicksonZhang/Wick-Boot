package com.wick.boot.module.system.model.dto.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台管理 - 角色信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemRoleDTO {

    @ApiModelProperty(value = "角色ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "角色名称", example = "系统管理员")
    private String name;

    @ApiModelProperty(value = "角色编码", example = "ADMIN")
    private String code;

    @ApiModelProperty(value = "角色状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "排序", example = "2")
    private Integer sort;

    @ApiModelProperty(value = "创建时间", example = "2021-03-25 12:39:54")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创更新时间", example = "2021-03-25 12:39:54")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
