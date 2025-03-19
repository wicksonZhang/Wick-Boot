package com.wick.boot.module.system.model.vo.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 通知公告新增参数
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
@Getter
@Setter
@ApiModel(value = "SystemNoticeAddVO", description = "通知公告新增参数")
public class SystemNoticeAddVO {

    @ApiModelProperty(value = "通知标题", required = true)
    @NotBlank(message = "通知标题不能为空")
    private String title;

    @ApiModelProperty(value = "通知内容", required = true)
    @NotBlank(message = "通知内容不能为空")
    private String content;

    @ApiModelProperty(value = "通知类型（关联字典编码：notice_type）", required = true)
    @NotNull(message = "通知类型（关联字典编码：notice_type）不能为空")
    private Integer type;

    @ApiModelProperty(value = "通知等级（字典code：notice_level）")
    private String level;

    @ApiModelProperty(value = "目标类型（1: 全体, 2: 指定）", required = true)
    @NotNull(message = "目标类型（1: 全体, 2: 指定）不能为空")
    private Integer targetType;

    @ApiModelProperty(value = "目标人ID集合（多个使用英文逗号,分割）", required = true)
    @NotBlank(message = "目标人ID集合（多个使用英文逗号,分割）不能为空")
    private String targetUserIds;

    @ApiModelProperty(value = "发布人ID")
    private Long publisherId;

    @ApiModelProperty(value = "发布状态（0: 未发布, 1: 已发布, -1: 已撤回）", required = true)
    @NotNull(message = "发布状态（0: 未发布, 1: 已发布, -1: 已撤回）不能为空")
    private Integer publishStatus;

    @ApiModelProperty(value = "发布时间", required = true)
    @NotNull(message = "发布时间不能为空")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "撤回时间", required = true)
    @NotNull(message = "撤回时间不能为空")
    private LocalDateTime revokeTime;

}


