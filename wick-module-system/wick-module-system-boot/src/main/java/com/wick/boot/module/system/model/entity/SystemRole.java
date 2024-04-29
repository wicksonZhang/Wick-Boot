package com.wick.boot.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 系统管理 - 角色信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("system_role")
public class SystemRole extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 角色状态(1-正常；0-停用)
     */
    private Integer status;

    /**
     * 数据权限(1-全部数据权限；2-自定数据权限；3-部门数据权限；4-部门及以下数据权限；5-仅本人数据权限)
     */
    private Integer dataScope;

    /**
     * 菜单id
     */
    @TableField(exist = false)
    private List<Long> menuIds;

    /**
     * 权限ids
     */
    @TableField(exist = false)
    private List<Long> permissionIds;

}
