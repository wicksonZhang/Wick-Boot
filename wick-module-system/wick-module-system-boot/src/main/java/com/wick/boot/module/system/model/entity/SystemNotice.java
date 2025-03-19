package com.wick.boot.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 通知公告-实体类
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_notice")
public class SystemNotice extends BaseDO {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知类型（关联字典编码：notice_type）
     */
    private Integer type;

    /**
     * 通知等级（字典code：notice_level）
     */
    private String level;

    /**
     * 目标类型（1: 全体, 2: 指定）
     */
    private Integer targetType;

    /**
     * 目标人ID集合（多个使用英文逗号,分割）
     */
    private String targetUserIds;

    /**
     * 发布人ID
     */
    private Long publisherId;

    /**
     * 发布状态（0: 未发布, 1: 已发布, -1: 已撤回）
     */
    private Integer publishStatus;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 撤回时间
     */
    private LocalDateTime revokeTime;

}