package cn.wickson.security.system.model.dto;

import cn.wickson.security.system.enums.MenuTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台管理-菜单返回DTO
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemMenuDTO {

    @ApiModelProperty(value = "菜单ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "父菜单ID", example = "0")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称", example = "系统管理")
    private String name;

    @ApiModelProperty(value = "菜单图标", example = "system")
    private String icon;

    @ApiModelProperty(value = "路由路径", example = "/system")
    private String path;

    @ApiModelProperty(value = "组件路径", example = "Layout")
    private String component;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "状态(0-->禁用、1-->开启)", example = "1")
    private Integer visible;

    @ApiModelProperty(value = "跳转路径", example = "/system/user")
    private String redirect;

    @ApiModelProperty(value = "菜单类型", example = "CATALOG")
    private MenuTypeEnum type;

    @ApiModelProperty(value = "子菜单集合", example = "")
    private List<SystemMenuDTO> children;

    @ApiModelProperty(value = "按钮权限标识", example = "")
    private String perm;

    @ApiModelProperty(value = "创建时间", example = "2024-03-25 12:39:54")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2024-03-25 12:39:54")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
