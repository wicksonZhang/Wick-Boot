package cn.wickson.security.system.model.entity;

import cn.wickson.security.commons.model.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * 系统管理 - 用户信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_user")
public class SystemUser extends BaseDO {

    @TableId
    private BigInteger id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别((1:男;2:女))
     */
    private Integer gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 用户状态((1:正常;0:禁用))
     */
    private Integer status;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;

}

