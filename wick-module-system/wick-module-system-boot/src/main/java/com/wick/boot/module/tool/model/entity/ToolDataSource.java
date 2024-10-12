package com.wick.boot.module.tool.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 数据源配置实体
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
@Getter
@Setter
@TableName("tool_data_source")
public class ToolDataSource extends BaseDO {

    private static final long serialVersionUID=1L;

    /**
     * 主键编号
     */
    private Long id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 数据源连接
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

}