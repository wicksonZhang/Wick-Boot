package com.wick.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.common.core.model.entity.BaseDO;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统管理 - 字典类型表
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("system_dict_type")
public class SystemDictType extends BaseDO {

    /**
     * 字典主键
     */
    @TableId
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 状态
     * 枚举 {@link com.wick.common.core.enums.CommonStatusEnum}
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
