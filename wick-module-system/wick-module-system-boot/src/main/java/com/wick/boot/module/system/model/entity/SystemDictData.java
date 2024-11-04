package com.wick.boot.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统管理 - 字典数据表
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("system_dict_data")
public class SystemDictData extends BaseDO {

    /**
     * 字典数据编号
     */
    @TableId
    private Long id;

    /**
     * 字典类型
     * <p>
     * 冗余 {@link SystemDictType#getDictCode()}
     */
    private String dictCode;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private Integer value;

    /**
     * 字典排序
     */
    private Integer sort;

    /**
     * 标签类型
     */
    private String tagType;

    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
