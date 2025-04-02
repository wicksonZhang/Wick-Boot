package com.wick.boot.module.system.model.dto.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

import java.io.Serializable;

/**
 * 通知公告-DTO
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
@Getter
@Setter
@ApiModel(value = "SystemNoticeDTO对象", description = "通知公告视图DTO")
public class SystemNoticeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "通知标题")
    private String title;

    @ApiModelProperty(value = "通知内容")
    private String content;

    @ApiModelProperty(value = "通知类型（关联字典编码：notice_type）")
    private Integer type;

    @ApiModelProperty(value = "通知等级（字典code：notice_level）")
    private String level;

    @ApiModelProperty(value = "目标类型（1: 全体, 2: 指定）")
    private Integer targetType;

    @ApiModelProperty(value = "目标人ID集合（多个使用英文逗号,分割）")
    private String targetUserIds;

    @ApiModelProperty(value = "发布人名称")
    private String publisherName;

    @ApiModelProperty(value = "发布状态（0: 未发布, 1: 已发布, -1: 已撤回）")
    private Integer publishStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "撤回时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime revokeTime;

}


