package com.wick.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.common.core.model.entity.BaseDO;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统管理 - 字典数据表
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
     * 冗余 {@link SystemDictType#getCode()}
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 字典排序
     */
    private Integer sort;

    /**
     * 状态
     * <p>
     * 枚举 {@link com.wick.common.core.enums.CommonStatusEnum}
     */
    private Integer status;

    /**
     * 颜色类型
     * <p>
     * 对应到 element-ui 为 default、primary、success、info、warning、danger
     */
    private String colorType;

    /**
     * css 样式
     */
    private String cssClass;

    /**
     * 备注
     */
    private String remark;

}
