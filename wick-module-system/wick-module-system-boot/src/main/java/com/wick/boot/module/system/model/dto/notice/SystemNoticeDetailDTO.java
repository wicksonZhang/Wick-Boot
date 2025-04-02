package com.wick.boot.module.system.model.dto.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 *
 */
@Getter
@Setter
@ApiModel(value = "SystemNoticeDetailDTO对象", description = "通知公告视图DTO")
public class SystemNoticeDetailDTO {

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

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "发布人名称")
    private String publisherName;

    @ApiModelProperty(value = "发布状态（0: 未发布, 1: 已发布, -1: 已撤回）")
    private Integer publishStatus;

}
