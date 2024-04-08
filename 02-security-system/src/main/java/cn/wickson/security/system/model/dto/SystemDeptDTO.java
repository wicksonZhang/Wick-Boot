package cn.wickson.security.system.model.dto;

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
public class SystemDeptDTO {

    @ApiModelProperty(value = "部门id", example = "1")
    private Long id;

    @ApiModelProperty(value = "部门名称", example = "Nexus")
    private String name;

    @ApiModelProperty(value = "父级id", example = "0")
    private Long parentId;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "状态(1->正常；0->禁用)", example = "1")
    private Integer status;

    @ApiModelProperty(value = "部门子级信息")
    private List<SystemDeptDTO> children;

    @ApiModelProperty(value = "创建时间", example = "2024-04-06 22:11:44")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2024-04-06 22:11:44")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
