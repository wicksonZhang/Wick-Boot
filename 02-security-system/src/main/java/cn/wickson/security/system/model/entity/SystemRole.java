package cn.wickson.security.system.model.entity;

import cn.wickson.security.commons.model.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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
     * 数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)
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
